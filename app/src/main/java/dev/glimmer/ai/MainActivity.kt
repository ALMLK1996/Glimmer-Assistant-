package dev.glimmer.ai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.glimmer.ai.ui.onboarding.PermissionScreen
import dev.glimmer.ai.ui.theme.GlimmerTheme
import dev.glimmer.ai.util.PermissionUtils
import dev.glimmer.overlay.LightBeingOverlayService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GlimmerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var overlayGranted by remember {
                        mutableStateOf(PermissionUtils.canDrawOverlays(this))
                    }
                    var accessibilityEnabled by remember {
                        mutableStateOf(PermissionUtils.isAccessibilityServiceEnabled(this))
                    }

                    // Re-check when the user returns from Settings
                    val lifecycleOwner = LocalLifecycleOwner.current
                    LaunchedEffect(lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_RESUME) {
                                overlayGranted = PermissionUtils.canDrawOverlays(this@MainActivity)
                                accessibilityEnabled = PermissionUtils.isAccessibilityServiceEnabled(this@MainActivity)
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                    }

                    if (overlayGranted && accessibilityEnabled) {
                        // Permissions are ready — start the overlay service
                        LaunchedEffect(Unit) {
                            val intent = Intent(this@MainActivity, LightBeingOverlayService::class.java).apply {
                                action = LightBeingOverlayService.ACTION_SHOW
                            }
                            startForegroundService(intent)
                        }

                        // Temporary placeholder until a real home screen exists
                        androidx.compose.material3.Text(
                            text = "Glimmer is running.\nLight Being should now be able to appear.",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        PermissionScreen(
                            overlayGranted = overlayGranted,
                            accessibilityEnabled = accessibilityEnabled,
                            onContinue = {
                                // Will only be enabled when both are granted
                                val intent = Intent(this@MainActivity, LightBeingOverlayService::class.java).apply {
                                    action = LightBeingOverlayService.ACTION_SHOW
                                }
                                startForegroundService(intent)
                            }
                        )
                    }
                }
            }
        }
    }
}
