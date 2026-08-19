package dev.glimmer.core.model

data class LightBeingState(
    val presence: PresenceState = PresenceState.HIDDEN,
    val density: Float = 0.6f,
    val energy: Float = 0.4f,
    val isListening: Boolean = false,
    val isThinking: Boolean = false
)
