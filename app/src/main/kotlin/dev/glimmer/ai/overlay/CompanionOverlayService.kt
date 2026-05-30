package dev.glimmer.ai.overlay

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.glimmer.ai.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CompanionOverlayService : Service() {

    @Inject
    lateinit var overlayManager: OverlayManager

    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        Timber.d("CompanionOverlayService created")
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("CompanionOverlayService started")
        overlayManager.showCompanion()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("CompanionOverlayService destroyed")
        overlayManager.dismissCompanion()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Glimmer Assistant")
        .setContentText("Your companion is active")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setOngoing(true)
        .build()

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "glimmer_companion_channel"
    }
}
