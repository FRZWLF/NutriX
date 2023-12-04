package com.sem.nutrix.presentation.screens.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var loadingState = mutableStateOf(false)
        private set
    var isLoading = mutableStateOf(false)
        private set

    var emailState by  mutableStateOf("")
        private set
    var passwordState by mutableStateOf("")
        private set

    fun changeEmail(email: String) {
        emailState = email
    }

    fun changePassword(password: String) {
        passwordState = password
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

}