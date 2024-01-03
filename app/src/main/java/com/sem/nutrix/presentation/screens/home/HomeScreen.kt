package com.sem.nutrix.presentation.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseUser
import com.sem.nutrix.R
import com.sem.nutrix.model.EmailPasswordState
import com.sem.nutrix.presentation.components.GoogleButton
import com.sem.nutrix.presentation.screens.auth.AuthViewModel
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.TAG

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
    loadingState: Boolean,
    isSignInSuccess: Boolean,
    onSignOutClicked: () -> Unit,
    oneTapState: OneTapSignInState,
    onSuccessfulSignIn: () -> Unit,
    onFailedFirebaseSignIn: (Exception) -> Unit,
    onDialogDismissed: (String) -> Unit,
    onSyncClicked: () -> Unit,
    navigateToProductadd: () -> Unit
){
    NavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked,
        loadingState = loadingState,
        onSyncClicked = onSyncClicked
    ) {
        Scaffold (
            topBar = {
                HomeTopBar(onMenuClicked = onMenuClicked)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = navigateToProductadd){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "New Product Icon"
                    )
                }

            },
            content = {}
        )
    }
    Log.d("Hallo", "Hallo")
    DisposableEffect(isSignInSuccess) {
        ChangeApi(
            onSuccessfulSignIn = {
                onSuccessfulSignIn() },
            onDialogDismissed = { message ->
                onDialogDismissed(message)
            }
        )
        onDispose {}
    }

}


fun ChangeApi(
    onSuccessfulSignIn: () -> Unit,
    onDialogDismissed: (String) -> Unit,
) {

//    Log.d("drin", "$isSignInSuccess")
//    if (isSignInSuccess) {
        Log.d("drin", "drine")
        try {
            onSuccessfulSignIn()
        } catch (e: ApiException) {
            Log.e(TAG, "${e.message}")
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onDialogDismissed("Dialog Canceled.")
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    onDialogDismissed("Network Error.")
                }
                else -> {
                    onDialogDismissed(e.message.toString())
                }
            }
        }
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    loadingState: Boolean,
    onSignOutClicked: () -> Unit,
    onSyncClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val isSignInSuccess = viewModel.isSignInSuccess

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            modifier = Modifier.size(250.dp),
                            painter = painterResource(id = R.drawable.logologo),
                            contentDescription = "Logo Image"
                        )
                    }
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                GoogleButton(
                                    loadingState = loadingState,
                                    onClick = onSyncClicked,
                                    primaryText = stringResource(id = R.string.google_sign_up)
                                )

                            }
                        },
                        selected = false,
                        onClick = onSyncClicked
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.google_app_logo),
                                    contentDescription = "Google Logo"
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Sign Out",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        selected = false,
                        onClick = onSignOutClicked
                    )
                }
            )

        },
        content = content
    )
}