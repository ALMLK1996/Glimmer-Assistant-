package dev.glimmer.overlay

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import dev.glimmer.core.model.PresenceState
import dev.glimmer.overlay.ui.LightBeingOverlayRoot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class LightBeingOverlayService : Service(), LifecycleOwner, SavedStateRegistryOwner {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var presenceController: PresenceController
    private var windowManager: WindowManager? = null
    private var overlayView: ComposeView? = null

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    private val channelId = "glimmer_overlay"

    override fun onCreate() {
        super.onCreate()

        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        presenceController = PresenceController(scope)
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())

        lifecycleRegistry.currentState = Lifecycle.State.STARTED

        // Observe presence changes and keep the overlay in sync
        scope.launch {
            presenceController.state.collectLatest { state ->
                when (state.presence) {
                    PresenceState.VISIBLE,
                    PresenceState.MATERIALIZING,
                    PresenceState.IDLE_PERFORMANCE,
                    PresenceState.FADING -> ensureOverlayVisible(state.presence)

                    PresenceState.HIDDEN -> removeOverlay()
                }
            }
        }

        Timber.d("LightBeingOverlayService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SHOW -> presenceController.onUserInteraction()
            ACTION_HIDE -> presenceController.onUserBrowsing()
            ACTION_USER_ACTIVE -> presenceController.onUserBrowsing()
            ACTION_STOP -> {
                presenceController.forceHide()
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun ensureOverlayVisible(presence: PresenceState) {
        if (overlayView != null) {
            // View already exists; Compose will recompose from the collected state
            return
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 72
            y = 200
        }

        val view = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@LightBeingOverlayService)
            setViewTreeSavedStateRegistryOwner(this@LightBeingOverlayService)

            setContent {
                val state by presenceController.state.collectAsState()
                LightBeingOverlayRoot(presence = state.presence)
            }
        }

        try {
            windowManager?.addView(view, params)
            overlayView = view
            lifecycleRegistry.currentState = Lifecycle.State.RESUMED
            Timber.d("Overlay attached")
        } catch (e: Exception) {
            Timber.e(e, "Failed to attach overlay")
            overlayView = null
        }
    }

    private fun removeOverlay() {
        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                Timber.e(e, "Failed to remove overlay")
            }
        }
        overlayView = null
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            "Glimmer Presence",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Keeps the Light Being layer alive"
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Glimmer")
            .setContentText("Light Being is active")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    override fun onDestroy() {
        presenceController.forceHide()
        removeOverlay()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val ACTION_SHOW = "dev.glimmer.overlay.SHOW"
        const val ACTION_HIDE = "dev.glimmer.overlay.HIDE"
        const val ACTION_USER_ACTIVE = "dev.glimmer.overlay.USER_ACTIVE"
        const val ACTION_STOP = "dev.glimmer.overlay.STOP"
        private const val NOTIFICATION_ID = 1001
    }
}
