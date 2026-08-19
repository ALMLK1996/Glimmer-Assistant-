package dev.glimmer.character

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
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
import dev.glimmer.core.model.PresenceState
import kotlin.math.sin

@Composable
fun LightBeingView(
    presence: PresenceState,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "light_being")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val drift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "drift"
    )

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val baseRadius = size.minDimension * 0.28f * pulse

        // Soft outer aura
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0x3322D3EE),
                    Color(0x226366F1),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius * 2.4f
            ),
            radius = baseRadius * 2.4f,
            center = center
        )

        // Mid energy layer
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

        // Subtle inner nucleus
        val nucleusOffset = Offset(
            x = center.x + sin(Math.toRadians(drift.toDouble())).toFloat() * 3f,
            y = center.y
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.9f),
            radius = baseRadius * 0.28f,
            center = nucleusOffset
        )
    }
}
