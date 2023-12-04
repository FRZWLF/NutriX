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
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriXTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Screen.Registration.route,
                    navController = navController,
                    onDataLoaded = {

                    }
                )
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.create(Constants.APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.Home.route
    else Screen.Registration.route
}