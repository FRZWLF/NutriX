package com.sem.nutrix

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.sem.nutrix.navigation.Screen
import com.sem.nutrix.navigation.SetupNavGraph
import com.sem.nutrix.presentation.screens.home.HomeViewModel
import com.sem.nutrix.ui.theme.NutriXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var SplashOpened = true
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        checkPermissions()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            Log.d("Win", "Win")
            homeViewModel.handleSignInResult(data, this)
        } else {
            Log.d("Failure", "Failure")
        }
    }

    private fun checkPermissions() {
        // Überprüfe hier die benötigten Berechtigungen
        val locationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val activityRecognitionPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACTIVITY_RECOGNITION
        )

        // Wenn Berechtigungen nicht erteilt sind, frage sie an
        if (locationPermission != PackageManager.PERMISSION_GRANTED ||
            activityRecognitionPermission != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        // Fordere hier die benötigten Berechtigungen an
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACTIVITY_RECOGNITION
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Überprüfe die Ergebnisse der Berechtigungsanfrage
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    // Eine oder mehrere Berechtigungen wurden nicht erteilt
                    // Handle dies entsprechend, z.B. informiere den Benutzer
                    // und schließe die App oder zeige eine Warnung an.
                }
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }

}
