package dev.glimmer.ai.overlay

import android.content.Context
import android.view.WindowManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.glimmer.ai.ai.expression.CompanionExpressionEngine
import dev.glimmer.ai.ai.expression.Emotion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverlayManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val windowManager: WindowManager,
    private val expressionEngine: CompanionExpressionEngine
) {

    private var companionView: CompanionView? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    fun showCompanion() {
        try {
            if (companionView != null) {
                Timber.w("Companion already shown")
                return
            }

            companionView = CompanionView(context).apply {
                val params = WindowManager.LayoutParams().apply {
                    type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    format = android.graphics.PixelFormat.TRANSLUCENT
                    flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    width = 200
                    height = 200
                    alpha = 0.95f
                    x = 0
                    y = 0
                }
                windowManager.addView(this, params)
            }
            Timber.d("Companion overlay shown")
        } catch (e: Exception) {
            Timber.e(e, "Failed to show companion overlay")
        }
    }

    fun dismissCompanion() {
        try {
            companionView?.let {
                windowManager.removeView(it)
                companionView = null
            }
            Timber.d("Companion overlay dismissed")
        } catch (e: Exception) {
            Timber.e(e, "Failed to dismiss companion overlay")
        }
    }

    fun expressEmotion(emotion: Emotion) {
        companionView?.let {
            expressionEngine.express(emotion, intensity = 1.0f)
            Timber.d("Companion expressed emotion: $emotion")
        }
    }

    fun updatePosition(x: Int, y: Int) {
        companionView?.let {
            try {
                val params = it.layoutParams as WindowManager.LayoutParams
                params.x = x
                params.y = y
                windowManager.updateViewLayout(it, params)
            } catch (e: Exception) {
                Timber.e(e, "Failed to update overlay position")
            }
        }
    }
}
