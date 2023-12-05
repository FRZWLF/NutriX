package com.sem.nutrix.presentation.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sem.nutrix.model.EmailPasswordState
import com.sem.nutrix.presentation.screens.auth.AuthViewModel
import com.sem.nutrix.presentation.screens.auth.LoginWithEmailPassword
import com.sem.nutrix.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    authenticated: Boolean,
    loggedin: Boolean,
    toRegistration: Boolean,
    loadingState: Boolean,
    isLoading: Boolean,
    emailPasswordState: EmailPasswordState,
    oneTapState: OneTapSignInState,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
    toRegistrationClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onEmailPasswordReceived: (String, String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val registrationState: AuthViewModel = viewModel()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            // Einbettung von ContentWithMessageBar, um Nachrichten anzuzeigen
            ContentWithMessageBar(messageBarState = messageBarState) {
                // AuthContent wird aufgerufen, um den eigentlichen Inhalt anzuzeigen
                LoginContent(
                    loadingState = loadingState,
                    isLoading = isLoading,
                    onButtonClicked = onButtonClicked,
                    onLoginButtonClicked = onLoginButtonClicked,
                    toRegistrationClicked = toRegistrationClicked
                )
            }
        }
    )

    // OneTapSignInWithGoogle wird aufgerufen, um Google-Anmeldung zu unterstützen
    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            // Callback-Funktion für den Empfang des Google-Token-IDs
            onTokenIdReceived(tokenId)
        },
        onDialogDismissed = { message ->
            // Callback-Funktion für das Schließen des Dialogs
            onDialogDismissed(message)
        }
    )

    // RegisterWithEmailPassword wird aufgerufen, um die E-Mail- und Passwort-Registrierung zu unterstützen
    LoginWithEmailPassword(
        emailPasswordState = emailPasswordState,
        onEmailPasswordReceived = { email, password ->
            // Callback-Funktion für den Empfang von E-Mail und Passwort
            onEmailPasswordReceived(email, password)
        },
        onDialogDismissed = { message ->
            // Callback-Funktion für das Schließen des Dialogs
            onDialogDismissed(message)
        }
    )

    // LaunchedEffect wird verwendet, um auf Authentifizierungs- oder Registrierungsänderungen zu reagieren
    LaunchedEffect(key1 = authenticated) {
        if (authenticated) {
            // Navigieren zur Home-Seite, wenn der Benutzer authentifiziert ist
            navigateToHome()
        }
    }

    LaunchedEffect(key1 = loggedin) {
        if (loggedin) {
            // Navigieren zur Registrierungsseite, wenn der Benutzer registriert ist
            navigateToHome()
        }
    }

    LaunchedEffect(key1 = toRegistration) {
        if (toRegistration) {
            registrationState.setToRegistration(false)
            navigateToRegister()
        }
    }
}


