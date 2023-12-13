package com.sem.nutrix.presentation.screens.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sem.nutrix.model.Product
import com.sem.nutrix.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    var authenticated = mutableStateOf(false)
        private set
    var registered = mutableStateOf(false)
        private set
    var loggedin = mutableStateOf(false)
        private set
    var loadingState = mutableStateOf(false)
        private set
    var isLoading = mutableStateOf(false)
        private set
    var emailState by  mutableStateOf("")
        private set
    var passwordState by mutableStateOf("")
        private set
    var firstNameState by  mutableStateOf("")
        private set
    var lastNameState by mutableStateOf("")
        private set
    var toLogin = mutableStateOf(false)
        private set
    var toRegistration = mutableStateOf(false)
        private set

    var totalKcal by mutableIntStateOf(0)
        private set

    fun changeTestTextState(products: List<Product>) {
        totalKcal = products.sumOf { it.kcal }

    }

    fun changeEmail(email: String) {
        emailState = email
    }

    fun changePassword(password: String) {
        passwordState = password
    }

    fun changeFirstName(firstName: String) {
        firstNameState = firstName
    }

    fun changeLastName(lastName: String) {
        lastNameState = lastName
    }

    fun setToLogin(routing: Boolean){
        toLogin.value = routing
    }

    fun setToRegistration(routing: Boolean){
        toRegistration.value = routing
    }

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }
    fun setIsLoading(loading: Boolean) {
        isLoading.value = loading
    }

    fun signInWithMongoAtlas(
        tokenId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    App.create(APP_ID).login(
                        Credentials.jwt(tokenId)
                    ).loggedIn
                }
                withContext(Dispatchers.Main) {
                    if (result) {
                        onSuccess()
                        delay(600)
                        authenticated.value = true
                    } else {
                        onError(Exception("User is not logged in."))
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }

    fun signUpWithMongoAtlas(
        email:String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("Drin", email)
                val result = withContext(Dispatchers.IO) {
                    App.create(APP_ID).emailPasswordAuth.registerUser(
                        email,
                        password
                    )
                }

                withContext(Dispatchers.Main) {
                    if (result != null) {
                        onSuccess()
                        delay(600)
                        registered.value = true
                    } else {
                        onError(Exception("User is not logged in."))
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }
    fun EmailPasswordSignInWithMongoAtlas(
        email:String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("Drin", email)
                val result = withContext(Dispatchers.IO) {
                    val emailPasswordCredentials = Credentials.emailPassword(email, password)
                    App.create(APP_ID).login(
                        emailPasswordCredentials
                    ).loggedIn
                }

                withContext(Dispatchers.Main) {
                    if (result) {
                        onSuccess()
                        delay(600)
                        loggedin.value = true
                    } else {
                        onError(Exception("User is not logged in."))
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }
}


