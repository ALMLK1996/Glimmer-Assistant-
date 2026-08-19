package dev.glimmer.overlay.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.glimmer.core.model.PresenceState
import dev.glimmer.character.LightBeingView

@Composable
fun LightBeingOverlayRoot(
    presence: PresenceState,
    onDismissRequest: () -> Unit
) {
    if (presence == PresenceState.HIDDEN || presence == PresenceState.FADING) {
        return
    }

    Box(modifier = Modifier.size(160.dp)) {
        LightBeingView(
            presence = presence,
            modifier = Modifier.size(160.dp)
        )
    }
}
