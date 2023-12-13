package com.sem.nutrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.sem.nutrix.navigation.Screen
import com.sem.nutrix.navigation.SetupNavGraph
import com.sem.nutrix.ui.theme.NutriXTheme
import com.sem.nutrix.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepSplashOpened = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            keepSplashOpened
        }
        setContent {
            NutriXTheme(dynamicColor = false) {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDataLoaded = {
                        keepSplashOpened = false
                    }
                )
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.create(Constants.APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.MealProductList.route //Home
    else Screen.Login.route
}