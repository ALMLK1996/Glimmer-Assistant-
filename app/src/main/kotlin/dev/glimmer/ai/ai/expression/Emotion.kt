package dev.glimmer.ai.ai.expression

enum class Emotion {
    HAPPY,
    FOCUSED,
    CARING,
    PROTECTIVE,
    CURIOUS,
    CALM,
    SURPRISED,
    CONFUSED,
    THINKING,
    ENERGETIC;

    fun getIntensity(): Float = when (this) {
        HAPPY -> 1.0f
        ENERGETIC -> 1.0f
        SURPRISED -> 0.9f
        CURIOUS -> 0.8f
        FOCUSED -> 0.7f
        THINKING -> 0.6f
        CALM -> 0.5f
        CARING -> 0.8f
        PROTECTIVE -> 0.85f
        CONFUSED -> 0.6f
    }
}
