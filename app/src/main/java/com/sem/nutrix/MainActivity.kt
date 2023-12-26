package com.sem.nutrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.sem.nutrix.navigation.Screen
import com.sem.nutrix.navigation.SetupNavGraph
import com.sem.nutrix.ui.theme.NutriXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var SplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            SplashOpened
        }
        setContent {
            NutriXTheme(dynamicColor = false) {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Screen.SplashScreen.route,
                    navController = navController,
                    onDataLoaded = {
                        SplashOpened = false
                    }
                )

            }
        }
    }
}
