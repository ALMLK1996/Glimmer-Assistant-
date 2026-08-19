package dev.glimmer.overlay

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import timber.log.Timber

/**
 * Uses Accessibility events to detect when the user is actively
 * interacting with the device.
 *
 * This is the most reliable way (without root) to know that the
 * user has returned to browsing and the Light Being should hide.
 */
class AccessibilityInteractionTracker(
    private val service: AccessibilityService
) : InteractionTracker {

    private var listener: InteractionTracker.Listener? = null

    override fun start(listener: InteractionTracker.Listener) {
        this.listener = listener
        Timber.d("Interaction tracker started")
    }

    override fun stop() {
        listener = null
        Timber.d("Interaction tracker stopped")
    }

    fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || listener == null) return

        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED,
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED,
            AccessibilityEvent.TYPE_VIEW_SCROLLED,
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED,
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED,
            AccessibilityEvent.TYPE_TOUCH_INTERACTION_START -> {
                listener?.onUserActive()
            }
        }
    }
}
