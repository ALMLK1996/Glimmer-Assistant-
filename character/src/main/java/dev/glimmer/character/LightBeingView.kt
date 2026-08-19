package dev.glimmer.character

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import dev.glimmer.core.model.PresenceState
import kotlin.math.sin

@Composable
fun LightBeingView(
    presence: PresenceState,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "light_being")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (presence == PresenceState.IDLE_PERFORMANCE) 1800 else 2800,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val drift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "drift"
    )

    val targetAlpha = when (presence) {
        PresenceState.HIDDEN -> 0f
        PresenceState.FADING -> 0f
        PresenceState.MATERIALIZING -> 0.7f
        PresenceState.VISIBLE -> 1f
        PresenceState.IDLE_PERFORMANCE -> 1f
    }

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing),
        label = "alpha"
    )

    val energyMultiplier = when (presence) {
        PresenceState.IDLE_PERFORMANCE -> 1.25f
        PresenceState.VISIBLE -> 1f
        else -> 0.85f
    }

    Canvas(
        modifier = modifier.graphicsLayer { this.alpha = alpha }
    ) {
        if (alpha <= 0.01f) return@Canvas

        val center = Offset(size.width / 2f, size.height / 2f)
        val baseRadius = size.minDimension * 0.27f * pulse * energyMultiplier

        // Outer aura
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0x3322D3EE),
                    Color(0x226366F1),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius * 2.5f
            ),
            radius = baseRadius * 2.5f,
            center = center
        )

        // Mid layer
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xAA22D3EE),
                    Color(0x6600B8D4),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius * 1.55f
            ),
            radius = baseRadius * 1.55f,
            center = center
        )

        // Core
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White,
                    Color(0xFFE0F2FE),
                    Color(0xCC22D3EE),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius
            ),
            radius = baseRadius,
            center = center
        )

        // Inner nucleus with slight drift
        val nucleusOffset = Offset(
            x = center.x + sin(Math.toRadians(drift.toDouble())).toFloat() * 4f,
            y = center.y + sin(Math.toRadians((drift * 0.7).toDouble())).toFloat() * 2.5f
        )

        drawCircle(
            color = Color.White.copy(alpha = 0.92f),
            radius = baseRadius * 0.26f,
            center = nucleusOffset
        )
    }
}
