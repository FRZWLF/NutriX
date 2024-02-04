package com.sem.nutrix.navigation

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sem.nutrix.Splash
import com.sem.nutrix.model.RequestState
import com.sem.nutrix.presentation.components.DisplayAlertDialog
import com.sem.nutrix.presentation.screens.auth.AuthViewModel
import com.sem.nutrix.presentation.screens.auth.RegistrationScreen
import com.sem.nutrix.presentation.screens.auth.rememberEmailPasswordState
import com.sem.nutrix.presentation.screens.home.HomeScreen
import com.sem.nutrix.presentation.screens.home.HomeViewModel
import com.sem.nutrix.presentation.screens.login.LoginScreen
import com.sem.nutrix.presentation.screens.mealList.MealListScreen
import com.sem.nutrix.presentation.screens.mealList.MealListViewModel
import com.sem.nutrix.util.Constants.APP_ID
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private fun getStartDestination(): String {
    val user = App.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.MealProductList.route //Home
    else Screen.Login.route
}
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
            navigateToMeal = {
                navController.navigate(Screen.MealProductList.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            },
            onDataLoaded = onDataLoaded
        )
        productaddRoute()
        mealRoute(
            navigateToWrite = {
                navController.navigate(Screen.ProductAdd.route)
            },
            navigateToWriteWithArgs = {
                navController.navigate(Screen.ProductAdd.passProductId(productId = it))
            },
            navigateBackToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded
        )
        barcodeRoute()
        splashRoute(
            navigateNext = {
                navController.navigate(getStartDestination()) {
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            },
            onDataLoaded = onDataLoaded
        )
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
            onSuccessfulFirebaseSignIn = { tokenId ->
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
            onFailedFirebaseSignIn = {
                messageBarState.addError(it)
                viewModel.setLoading(false)
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


fun NavGraphBuilder.homeRoute(
    navigateToMeal: () -> Unit,
    navigateToAuth: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var signOutDialogOpened by remember {mutableStateOf(false)}
        val scope = rememberCoroutineScope()
        val viewModel: HomeViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val context = LocalContext.current

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        HomeScreen(
            drawerState = drawerState,
            loadingState = loadingState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSyncClicked = {
                viewModel.openSignInActivity(context as Activity)
                viewModel.setLoading(true)
            },
            onSignOutClicked = {
               signOutDialogOpened = true
            },
            navigateToMeal = navigateToMeal
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

fun NavGraphBuilder.mealRoute(
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit,
    navigateBackToHome: () -> Unit,
    onDataLoaded: () -> Unit
){
    composable(route = Screen.MealProductList.route) {
        val viewModel: MealListViewModel = hiltViewModel()
        val products by viewModel.products
        val uiState = viewModel.uiState
        val context = LocalContext.current
        var deleteAllDialogOpened by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = products) {
            if (products !is RequestState.Loading) {
                onDataLoaded()
            }
        }

        MealListScreen(
            uiState = uiState,
            products = products,
            onDeleteProduct = {
                viewModel.deleteProduct(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Product deleted",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onError = {message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            },
            onDeleteAllClicked = { deleteAllDialogOpened = true },
            navigateToWrite = navigateToWrite,
            navigateToWriteWithArgs = navigateToWriteWithArgs,
            navigateBackToHome = navigateBackToHome,
        )

        DisplayAlertDialog(
            title = "Delete All Products",
            message = "Are you sure you want to permanently delete all your Products?",
            dialogOpened = deleteAllDialogOpened,
            onCloseDialog = { deleteAllDialogOpened = false},
            onYesClicked = {
                viewModel.deleteAllProducts(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "All Products Deleted.",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onError = {
                        Toast.makeText(
                            context,
                            if (it.message == "No Internet Connection!")
                                "We need an Internet Connection for this operation."
                            else it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }
}

fun NavGraphBuilder.barcodeRoute(){
    composable(route = Screen.Barcode.route) {

    }
}

fun NavGraphBuilder.splashRoute(
    navigateNext: () -> Unit,
    onDataLoaded: () -> Unit
){
    composable(route = Screen.SplashScreen.route){
        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }
        Splash(
            navigateNext = navigateNext
        )
    }
}
