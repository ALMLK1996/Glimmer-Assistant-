package dev.glimmer.overlay

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import timber.log.Timber

/**
 * Observes system-wide interaction events so the Light Being
 * knows when to hide itself.
 *
 * Later this service will also be used for limited system actions.
 */
class GlimmerAccessibilityService : AccessibilityService() {

    private var interactionTracker: AccessibilityInteractionTracker? = null

    override fun onServiceConnected() {
        super.onServiceConnected()

        serviceInfo = serviceInfo.apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or
                AccessibilityEvent.TYPE_VIEW_LONG_CLICKED or
                AccessibilityEvent.TYPE_VIEW_SCROLLED or
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED or
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED or
                AccessibilityEvent.TYPE_TOUCH_INTERACTION_START

            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            notificationTimeout = 80
        }

        interactionTracker = AccessibilityInteractionTracker(this)
        Timber.d("Glimmer accessibility service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        interactionTracker?.onAccessibilityEvent(event)
    }

    override fun onInterrupt() {
        Timber.w("Glimmer accessibility service interrupted")
    }

    override fun onDestroy() {
        interactionTracker?.stop()
        interactionTracker = null
        super.onDestroy()
    }

    fun getInteractionTracker(): AccessibilityInteractionTracker? = interactionTracker
}
