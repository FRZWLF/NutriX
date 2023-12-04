package com.sem.nutrix.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sem.nutrix.presentation.screens.auth.AuthViewModel
import com.sem.nutrix.presentation.screens.auth.LoginScreen
import com.sem.nutrix.presentation.screens.auth.RegistrationScreen
import com.sem.nutrix.presentation.screens.auth.rememberEmailPasswordState
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ){
        registrationRoute(
            navigateToLogin = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            },
            onDataLoaded = onDataLoaded
        )
        loginRoute(
            navigateToRegister = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            },
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded
        )
        homeRoute()
        productaddRoute()
        mealRoute()
        barcodeRoute()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.registrationRoute(
    navigateToLogin: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Registration.route) {
        val viewModel: AuthViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val registered by viewModel.registered
        val loadingState by viewModel.loadingState
        val isLoading by viewModel.isLoading
        val oneTapState = rememberOneTapSignInState()
        val emailPasswordState = rememberEmailPasswordState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        RegistrationScreen(
            authenticated = authenticated,
            registered = registered,
            loadingState = loadingState,
            isLoading = isLoading,
            oneTapState = oneTapState,
            emailPasswordState = emailPasswordState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onRegisterButtonClicked = {
                emailPasswordState.open()
                viewModel.setIsLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onEmailPasswordReceived = { email, password ->
                viewModel.signUpWithMongoAtlas(
                    email = email,
                    password = password,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Registrated!")
                        viewModel.setIsLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setIsLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToLogin = navigateToLogin
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.loginRoute(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit
){
    composable(route = Screen.Login.route) {
        val viewModel: AuthViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val registered by viewModel.registered
        val loadingState by viewModel.loadingState
        val isLoading by viewModel.isLoading
        val oneTapState = rememberOneTapSignInState()
        val emailPasswordState = rememberEmailPasswordState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        LoginScreen(
            authenticated = authenticated,
            registered = registered,
            loadingState = loadingState,
            isLoading = isLoading,
            oneTapState = oneTapState,
            emailPasswordState = emailPasswordState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onRegisterButtonClicked = {
                emailPasswordState.open()
                viewModel.setIsLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onEmailPasswordReceived = { email, password ->
                viewModel.signUpWithMongoAtlas(
                    email = email,
                    password = password,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Registrated!")
                        viewModel.setIsLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setIsLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToRegister = navigateToRegister,
            navigateToHome = navigateToHome
        )
    }
}


fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {

    }
}

fun NavGraphBuilder.productaddRoute(){
    composable(
        route = Screen.ProductAdd.route,
       // arguments = listOf(navArgument(name = ADD_SCREEN_ARGUMENT_KEY) {
       //     type = NavType.StringType
       //     nullable = true
       //     defaultValue = null
       // })
    ) {

    }
}

fun NavGraphBuilder.mealRoute(){
    composable(route = Screen.MealProductList.route) {

    }
}

fun NavGraphBuilder.barcodeRoute(){
    composable(route = Screen.Barcode.route) {

    }
}