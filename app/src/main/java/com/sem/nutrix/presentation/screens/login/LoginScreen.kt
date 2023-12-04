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
import com.sem.nutrix.model.EmailPasswordState
import com.sem.nutrix.presentation.screens.auth.AuthContent
import com.sem.nutrix.presentation.screens.auth.RegisterWithEmailPassword
import com.sem.nutrix.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    authenticated: Boolean,
    registered: Boolean,
    loadingState: Boolean,
    isLoading: Boolean,
    emailPasswordState: EmailPasswordState,
    oneTapState: OneTapSignInState,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onEmailPasswordReceived: (String, String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            // Einbettung von ContentWithMessageBar, um Nachrichten anzuzeigen
            ContentWithMessageBar(messageBarState = messageBarState) {
                // AuthContent wird aufgerufen, um den eigentlichen Inhalt anzuzeigen
                AuthContent(
                    loadingState = loadingState,
                    isLoading = isLoading,
                    onButtonClicked = onButtonClicked,
                    onRegisterButtonClicked = onRegisterButtonClicked
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
    RegisterWithEmailPassword(
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

    LaunchedEffect(key1 = registered) {
        if (registered) {
            // Navigieren zur Registrierungsseite, wenn der Benutzer registriert ist
            navigateToRegister()
        }
    }
}
