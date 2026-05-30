package dev.glimmer.ai

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class GlimmerApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initializeLogging()
        initializeWorkManager()
    }

    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private fun initializeWorkManager() {
        WorkManager.initialize(this, getWorkManagerConfiguration())
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setDefaultProcessName("${packageName}:glimmer_work")
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }

    /**
     * Release build logging tree that filters out debug logs
     */
    private class ReleaseTree : Timber.Tree() {
        override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?
        ) {
            if (priority == android.util.Log.ERROR || priority == android.util.Log.WARN) {
                // Log errors and warnings only
                android.util.Log.println(priority, tag ?: "Glimmer", message)
                t?.let { android.util.Log.e(tag ?: "Glimmer", "", it) }
            }
        }
    }
}
