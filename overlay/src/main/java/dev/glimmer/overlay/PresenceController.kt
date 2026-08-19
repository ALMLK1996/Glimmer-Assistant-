package dev.glimmer.overlay

import dev.glimmer.core.model.LightBeingState
import dev.glimmer.core.model.PresenceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Controls when the Light Being is visible, hidden, or in idle performance.
 *
 * Rules:
 * - Show when the user interacts (talks / commands)
 * - Hide as soon as the user returns to normal phone use
 * - After a period of true idle, enter performance mode
 */
class PresenceController(
    private val scope: CoroutineScope
) {

    private val _state = MutableStateFlow(LightBeingState())
    val state: StateFlow<LightBeingState> = _state.asStateFlow()

    private var idleJob: Job? = null
    private var fadeJob: Job? = null

    // How long the screen must stay untouched before idle performance starts
    private val idleThresholdMs = 12_000L

    // How long a fade-out takes
    private val fadeDurationMs = 450L

    fun onUserInteraction() {
        idleJob?.cancel()
        fadeJob?.cancel()

        _state.value = _state.value.copy(
            presence = PresenceState.VISIBLE,
            isListening = true,
            isThinking = false
        )
    }

    fun onUserBrowsing() {
        // User touched the screen or returned to normal use → hide quickly
        fadeJob?.cancel()
        idleJob?.cancel()

        fadeJob = scope.launch {
            _state.value = _state.value.copy(
                presence = PresenceState.FADING,
                isListening = false
            )
            delay(fadeDurationMs)
            _state.value = _state.value.copy(
                presence = PresenceState.HIDDEN
            )
            scheduleIdleCheck()
        }
    }

    fun onThinking() {
        _state.value = _state.value.copy(
            isThinking = true,
            density = 0.85f
        )
    }

    fun onFinishedThinking() {
        _state.value = _state.value.copy(
            isThinking = false,
            density = 0.6f
        )
    }

    private fun scheduleIdleCheck() {
        idleJob?.cancel()
        idleJob = scope.launch {
            delay(idleThresholdMs)
            // Still hidden and no new interaction → start performance
            if (_state.value.presence == PresenceState.HIDDEN) {
                _state.value = _state.value.copy(
                    presence = PresenceState.IDLE_PERFORMANCE,
                    energy = 0.7f
                )
            }
        }
    }

    fun forceHide() {
        idleJob?.cancel()
        fadeJob?.cancel()
        _state.value = LightBeingState(presence = PresenceState.HIDDEN)
    }
}
