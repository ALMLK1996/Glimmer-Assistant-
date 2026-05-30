package dev.glimmer.ai.overlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CompanionView(context: Context) : View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    private var animationProgress = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = minOf(width, height) / 2f * 0.8f

        // Draw companion circle
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Draw eyes
        val eyeRadius = radius * 0.15f
        val eyeDistance = radius * 0.35f
        paint.color = Color.WHITE
        canvas.drawCircle(centerX - eyeDistance, centerY - radius * 0.3f, eyeRadius, paint)
        canvas.drawCircle(centerX + eyeDistance, centerY - radius * 0.3f, eyeRadius, paint)

        // Draw pupils
        paint.color = Color.BLACK
        val pupilRadius = eyeRadius * 0.5f
        canvas.drawCircle(centerX - eyeDistance, centerY - radius * 0.3f, pupilRadius, paint)
        canvas.drawCircle(centerX + eyeDistance, centerY - radius * 0.3f, pupilRadius, paint)
    }

    fun setAnimationProgress(progress: Float) {
        animationProgress = progress
        invalidate()
    }
}
