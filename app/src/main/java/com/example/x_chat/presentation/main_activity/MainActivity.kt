package com.example.x_chat.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.x_chat.presentation.home_screen.HomeScreen
import com.example.x_chat.presentation.sign_in_screen.SignInScreen
import com.example.x_chat.ui.theme.X_ChatTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }
        setContent {
            X_ChatTheme {
                val systemController = rememberSystemUiController()
                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent
                    )
                }

                val result by viewModel.result.collectAsState()
                when (result) {
                    false -> Navigator(screen = SignInScreen)

                    true -> Navigator(screen = HomeScreen)

                }

            }

        }
    }
}