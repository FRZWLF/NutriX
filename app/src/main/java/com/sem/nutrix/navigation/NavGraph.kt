package com.sem.nutrix.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sem.nutrix.presentation.components.DisplayAlertDialog
import com.sem.nutrix.presentation.screens.auth.RegistrationScreen
import com.sem.nutrix.presentation.screens.auth.rememberEmailPasswordState
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.sem.nutrix.presentation.screens.home.HomeScreen
import com.sem.nutrix.util.Constants.APP_ID
import com.sem.nutrix.presentation.screens.auth.AuthViewModel

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
                navController.navigate(Screen.Registration.route)
            },
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded
        )
        homeRoute(
            navigateToProductadd = {
                navController.navigate(Screen.ProductAdd.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )
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
        val toLogin by viewModel.toLogin
        val oneTapState = rememberOneTapSignInState()
        val emailPasswordState = rememberEmailPasswordState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        RegistrationScreen(
            authenticated = authenticated,
            registered = registered,
            toLogin = toLogin,
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
            toLoginClicked = {
                viewModel.setToLogin(true)
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

fun NavGraphBuilder.loginRoute(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit
){
    composable(route = Screen.Login.route) {
        val viewModel: AuthViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loggedin by viewModel.loggedin
        val loadingState by viewModel.loadingState
        val isLoading by viewModel.isLoading
        val toRegistration by viewModel.toRegistration
        val oneTapState = rememberOneTapSignInState()
        val emailPasswordState = rememberEmailPasswordState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        LoginScreen(
            authenticated = authenticated,
            loggedin = loggedin,
            toRegistration = toRegistration,
            loadingState = loadingState,
            isLoading = isLoading,
            oneTapState = oneTapState,
            emailPasswordState = emailPasswordState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onLoginButtonClicked = {
                emailPasswordState.open()
                viewModel.setIsLoading(true)
            },
            toRegistrationClicked =  {
                viewModel.setToRegistration(true)
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
                viewModel.EmailPasswordSignInWithMongoAtlas(
                    email = email,
                    password = password,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Loggedin!")
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


@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(
    navigateToProductadd: () -> Unit,
    navigateToAuth: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var signOutDialogOpened by remember {mutableStateOf(false)}
        val scope = rememberCoroutineScope()
        HomeScreen(
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = {
               signOutDialogOpened = true
            },
            navigateToProductadd = navigateToProductadd
        )

        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure?",
            dialogOpened = signOutDialogOpened,
            onCloseDialog = {signOutDialogOpened = false},
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    if(user != null){
                        user.logOut()
                        withContext(Dispatchers.Main){
                            navigateToAuth()
                        }
                    }
                }
            }
        )
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
   // composable(route = Screen.Meal.route) {

    //}
}

fun NavGraphBuilder.barcodeRoute(){
    composable(route = Screen.Barcode.route) {

    }
}