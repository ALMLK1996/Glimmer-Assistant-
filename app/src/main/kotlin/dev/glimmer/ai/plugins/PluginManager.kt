package dev.glimmer.ai.plugins

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PluginManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val plugins = mutableMapOf<String, GlimmerPlugin>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun registerPlugin(plugin: GlimmerPlugin) {
        scope.launch {
            try {
                plugin.onInitialize(PluginContext(context))
                plugins[plugin.id] = plugin
                Timber.d("Plugin registered: ${plugin.name}")
            } catch (e: Exception) {
                Timber.e(e, "Failed to register plugin: ${plugin.name}")
            }
        }
    }

    fun unregisterPlugin(pluginId: String) {
        scope.launch {
            try {
                plugins[pluginId]?.onDestroy()
                plugins.remove(pluginId)
                Timber.d("Plugin unregistered: $pluginId")
            } catch (e: Exception) {
                Timber.e(e, "Failed to unregister plugin: $pluginId")
            }
        }
    }

    suspend fun processWithPlugins(input: String): PluginResponse? {
        return plugins.values
            .sortedByDescending { 0 }  // Can be sorted by priority
            .firstNotNullOfOrNull { plugin ->
                try {
                    plugin.onUserInput(input)
                } catch (e: Exception) {
                    Timber.e(e, "Error processing plugin: ${plugin.name}")
                    null
                }
            }
    }

    fun broadcastSystemEvent(event: SystemEvent) {
        scope.launch {
            plugins.values.forEach { plugin ->
                try {
                    plugin.onSystemEvent(event)
                } catch (e: Exception) {
                    Timber.e(e, "Error handling system event in plugin: ${plugin.name}")
                }
            }
        }
    }

    fun getPlugins(): Map<String, GlimmerPlugin> = plugins.toMap()
}
