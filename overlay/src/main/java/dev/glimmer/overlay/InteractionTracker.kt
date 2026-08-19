package dev.glimmer.overlay

/**
 * Reports high-level user activity so the PresenceController
 * can decide when to show, hide, or enter idle performance.
 */
interface InteractionTracker {

    fun start(listener: Listener)

    fun stop()

    interface Listener {
        /** User started interacting (touch, scroll, typing, opening apps...) */
        fun onUserActive()

        /** Screen has been quiet for a while */
        fun onUserIdle()
    }
}
