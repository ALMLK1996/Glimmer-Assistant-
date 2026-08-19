package dev.glimmer.overlay.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.glimmer.character.LightBeingView
import dev.glimmer.core.model.PresenceState

@Composable
fun LightBeingOverlayRoot(
    presence: PresenceState,
    onDismissRequest: () -> Unit = {}
) {
    // Even when fading we still render so the alpha animation can play
    if (presence == PresenceState.HIDDEN) return

    Box(modifier = Modifier.size(168.dp)) {
        LightBeingView(
            presence = presence,
            modifier = Modifier.size(168.dp)
        )
    }
}
