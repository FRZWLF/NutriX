package com.sem.nutrix.presentation.screens.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.sem.nutrix.model.EmailPasswordState
import com.sem.nutrix.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.TAG

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(
    authenticated: Boolean,
    registered: Boolean,
    toLogin: Boolean,
    loadingState: Boolean,
    isLoading: Boolean,
    emailPasswordState: EmailPasswordState,
    oneTapState: OneTapSignInState,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    toLoginClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onEmailPasswordReceived: (String, String) -> Unit,
//    onSuccessfulFirebaseSignIn: (String) -> Unit,
//    onFailedFirebaseSignIn: (Exception) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToLogin: () -> Unit,
) {
    val loginState: AuthViewModel = viewModel()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            ContentWithMessageBar(
                messageBarState = messageBarState,
                errorMaxLines = 5
                ) {
               AuthContent(
                    loadingState = loadingState,
                    isLoading = isLoading,
                    onButtonClicked = onButtonClicked,
                    onRegisterButtonClicked = onRegisterButtonClicked,
                    toLoginClicked = toLoginClicked
                )
            }
        }
    )

    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            onTokenIdReceived(tokenId)
//            val credential = GoogleAuthProvider.getCredential(tokenId, null)
//            FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if(task.isSuccessful) {
//                        onSuccessfulFirebaseSignIn(tokenId)
//                    } else {
//                        task.exception?.let { it -> onFailedFirebaseSignIn(it) }
//                    }
//                }
        },
        onDialogDismissed = { message ->
            onDialogDismissed(message)
        }
    )

    RegisterWithEmailPassword(
        emailPasswordState = emailPasswordState,
        onEmailPasswordReceived = { email, password ->
            onEmailPasswordReceived(email, password)

        },
        onDialogDismissed = { message ->
            onDialogDismissed(message)
        }
    )


    LaunchedEffect(key1 = authenticated) {
        if (authenticated) {
            navigateToLogin()
        }
    }

    LaunchedEffect(key1 = registered) {
        if (registered) {
            navigateToLogin()
        }
    }

    LaunchedEffect(key1 = toLogin) {
        if (toLogin) {
            loginState.setToLogin(false)
            navigateToLogin()
        }
    }
}

@Composable
fun rememberEmailPasswordState(): EmailPasswordState {
    return remember { EmailPasswordState() }
}

@Composable
fun RegisterWithEmailPassword(
    emailPasswordState: EmailPasswordState,
    onEmailPasswordReceived: (String, String) -> Unit,
    onDialogDismissed: (String) -> Unit,
) {
    val emailPassword: AuthViewModel = viewModel()
//    val messageBarState = rememberMessageBarState()

    if (emailPasswordState.opened) {
        try {
            val email = emailPassword.emailState
            val password = emailPassword.passwordState
            Log.d("HurraEmail", email)
            Log.d("HurraPassword", password)
            onEmailPasswordReceived(email, password)
            emailPasswordState.close()
        } catch (e: ApiException) {
            Log.e(TAG, "${e.message}")
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onDialogDismissed("Dialog Canceled.")
                    emailPasswordState.close()
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    onDialogDismissed("Network Error.")
                    emailPasswordState.close()
                }
                else -> {
                    onDialogDismissed(e.message.toString())
                    emailPasswordState.close()
                }
            }
        }
    }
//
}

@Composable
fun LoginWithEmailPassword(
    emailPasswordState: EmailPasswordState,
    onEmailPasswordReceived: (String, String) -> Unit,
    onDialogDismissed: (String) -> Unit,
) {
    val emailPassword: AuthViewModel = viewModel()
//    val messageBarState = rememberMessageBarState()

    if (emailPasswordState.opened) {
        try {
            val email = emailPassword.emailState
            val password = emailPassword.passwordState
            Log.d("HurraEmail", email)
            Log.d("HurraPassword", password)
            onEmailPasswordReceived(email, password)
            emailPasswordState.close()
        } catch (e: ApiException) {
            Log.e(TAG, "${e.message}")
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onDialogDismissed("Dialog Canceled.")
                    emailPasswordState.close()
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    onDialogDismissed("Network Error.")
                    emailPasswordState.close()
                }
                else -> {
                    onDialogDismissed(e.message.toString())
                    emailPasswordState.close()
                }
            }
        }
    }
}