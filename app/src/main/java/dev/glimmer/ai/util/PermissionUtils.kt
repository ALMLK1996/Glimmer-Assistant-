package dev.glimmer.ai.util

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import dev.glimmer.overlay.GlimmerAccessibilityService

object PermissionUtils {

    fun canDrawOverlays(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun isAccessibilityServiceEnabled(context: Context): Boolean {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)

        val target = GlimmerAccessibilityService::class.java.canonicalName ?: return false

        return enabledServices.any { info ->
            info.resolveInfo.serviceInfo.let { serviceInfo ->
                serviceInfo.packageName == context.packageName &&
                    serviceInfo.name == target
            }
        }
    }
}
