package dev.glimmer.ai.plugins

import android.content.Context

interface GlimmerPlugin {
    val id: String
    val name: String
    val version: String
    val description: String

    suspend fun onInitialize(context: PluginContext)
    suspend fun onUserInput(input: String): PluginResponse?
    suspend fun onSystemEvent(event: SystemEvent)
    fun getManifest(): PluginManifest
    suspend fun onDestroy()
}

data class PluginContext(
    val applicationContext: Context
)

data class PluginResponse(
    val text: String,
    val suggestedEmotion: String? = null,
    val action: SystemAction? = null,
    val priority: Int = 0  // 0-100, higher = more important
)

data class PluginManifest(
    val id: String,
    val name: String,
    val version: String,
    val author: String,
    val permissions: List<String>,
    val minimumApiLevel: Int = 28
)

data class SystemAction(
    val type: String,  // "OPEN_APP", "EXECUTE_TASK", etc.
    val target: String,
    val params: Map<String, String>? = null
)

data class SystemEvent(
    val type: String,  // "APP_OPENED", "NOTIFICATION", etc.
    val timestamp: Long = System.currentTimeMillis(),
    val data: Map<String, String>? = null
)
