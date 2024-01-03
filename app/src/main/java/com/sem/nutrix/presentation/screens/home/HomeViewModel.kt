package com.sem.nutrix.presentation.screens.home

import android.app.Activity
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataReadRequest.*
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.internal.Contexts.getApplication
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    private var fitnessOptions: FitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)

        .build()

    var isSignInSuccess by mutableStateOf(false)
        private set

    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1


    // Hier sollten Sie eine Callback-Schnittstelle oder LiveData verwenden,
    // um den Aktivitätsstatus oder die Benutzeroberfläche zu aktualisieren,
    // basierend auf dem Ergebnis der Berechtigungsüberprüfung
    var onPermissionResult: (Boolean) -> Unit = {}

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    private val gsc = GoogleSignIn.getClient(application, gso)

    fun openSignInActivity(activity: Activity) {
        gsc.signOut()
        val signInIntent = gsc.getSignInIntent()
        Log.d("Klick", "Hab geklickt!")
        activity.startActivityForResult(signInIntent, 1000)
    }


    fun handleSignInResult(data: Intent?, activity: Activity) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            task.getResult(ApiException::class.java)
            Log.d("Money", "${task.result.account}")
            checkGoogleFitPermission(activity)
            // Erfolgreicher SignIn, handle hier
            isSignInSuccess = true
            Log.d("Klick", "$isSignInSuccess")
            Toast.makeText(getApplication(), "Worked", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // Fehler beim SignIn, handle hier
            Log.d("Exception", "$e")
            Toast.makeText(getApplication(), "$e", Toast.LENGTH_SHORT).show()
        }
    }


    fun checkGoogleFitPermission(activity: Activity) {
        //context richtig setzen, da den angemeldeten User übergeben!
        val account = getGoogleAccount()
        Log.d("$account", "${account.email}")
        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                    activity,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    account,
                    fitnessOptions
                )
            Log.d("$account", "${account.email}")
        } else {
            Log.d("Easy", "Easy")
            // Zugriff auf Google Fit
            startDataReading(activity)
        }
    }

    private fun getGoogleAccount(): GoogleSignInAccount {
        return GoogleSignIn.getAccountForExtension(getApplication(), fitnessOptions)
    }

    fun startDataReading(activity: Activity){
        getTodayData(activity)

        subscribeAndGetRealtimeData(activity, DataType.TYPE_CALORIES_EXPENDED)
    }

    fun getTodayData(activity: Activity){
        Fitness.getHistoryClient(activity, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
            .addOnSuccessListener { dataSet ->
                if (dataSet.isEmpty) {
                    Log.d("GoogleFit", "Das DataSet ist leer.")
                } else {
                    val dataPoint = dataSet.dataPoints[0]
                    val caloriesField = dataPoint.getValue(Field.FIELD_CALORIES)

                    if (caloriesField != null) {
                        val calories = caloriesField.asFloat()
                        Log.d("GoogleFit", "Verbrannte Kalorien: $calories kcal")
                    } else {
                        Log.d("GoogleFit", "Kalorienfeld ist null.")
                    }
                }
//                // Daten erfolgreich abgerufen
//                val calories = if (dataSet.isEmpty) 0F else dataSet.dataPoints[0].getValue(Field.FIELD_CALORIES).asFloat()
//
//                // Aktuelles Datum formatieren
//                val currentDate = DateFormat.getDateInstance().format(Date())
//
//                // Daten in deiner Data Class speichern
//                val dailyCaloriesData = DailyCaloriesData(currentDate, calories)
//                Log.d("GoogleFit", "Heutige verbrannte Kalorien: $calories kcal")
//                Log.d("GoogleFit", "Heutiges Datum: $currentDate")
//                // Füge diese Zeile hinzu, um den Inhalt von dataSet zu überprüfen
//                Log.d("GoogleFit", "Inhalt von dataSet: $dataSet")
            }
            .addOnFailureListener { e ->
                // Fehler beim Abrufen der Daten
                Log.e("GoogleFit", "Fehler beim Abrufen der Daten: ${e.message}")

            }
    }

    fun subscribeAndGetRealtimeData(activity: Activity, dataType: DataType){
        Fitness.getRecordingClient(activity, getGoogleAccount())
            .subscribe(dataType)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully subscribed!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem subscribing.", e)
            }
    }
}

data class DailyCaloriesData(
    val date: String, // Datum, an dem die Daten abgerufen wurden
    val caloriesBurned: Float // Verbrannte Kalorien für diesen Tag
)