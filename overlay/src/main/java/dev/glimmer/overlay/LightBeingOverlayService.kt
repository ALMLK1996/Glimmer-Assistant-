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
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import dev.glimmer.core.model.PresenceState
import dev.glimmer.overlay.ui.LightBeingOverlayRoot
import timber.log.Timber

class LightBeingOverlayService : Service() {

    private var windowManager: WindowManager? = null
    private var overlayView: ComposeView? = null

    private val channelId = "glimmer_overlay"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SHOW -> showOverlay()
            ACTION_HIDE -> hideOverlay()
            ACTION_STOP -> stopSelf()
        }
        return START_STICKY
    }

    private fun showOverlay() {
        if (overlayView != null) return

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
            x = 80
            y = 220
        }

        val view = ComposeView(this).apply {
            setContent {
                LightBeingOverlayRoot(
                    presence = PresenceState.VISIBLE,
                    onDismissRequest = { hideOverlay() }
                )
            }
        }

        // Lifecycle owners are required for Compose in a Service context.
        // A more complete implementation will attach proper owners.
        overlayView = view

        try {
            windowManager?.addView(view, params)
            Timber.d("Light Being overlay shown")
        } catch (e: Exception) {
            Timber.e(e, "Failed to show overlay")
            overlayView = null
        }
    }

    private fun hideOverlay() {
        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                Timber.e(e, "Failed to remove overlay")
            }
        }
        overlayView = null
        Timber.d("Light Being overlay hidden")
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
        hideOverlay()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val ACTION_SHOW = "dev.glimmer.overlay.SHOW"
        const val ACTION_HIDE = "dev.glimmer.overlay.HIDE"
        const val ACTION_STOP = "dev.glimmer.overlay.STOP"
        private const val NOTIFICATION_ID = 1001
    }
}
