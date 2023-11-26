package com.sem.nutrix.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sem.nutrix.util.Constants.ADD_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavGraph(startDestination:String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ){
        authenticationRoute()
        homeRoute()
        productaddRoute()
        mealRoute()
        barcodeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(){
    composable(route = Screen.Authentication.route) {

    }
}

fun NavGraphBuilder.homeRoute(){
    composable(route = Screen.Home.route) {

    }
}



fun NavGraphBuilder.productaddRoute(){
//    composable(
      //  route = Screen.Add.route,
       // arguments = listOf(navArgument(name = ADD_SCREEN_ARGUMENT_KEY) {
       //     type = NavType.StringType
       //     nullable = true
       //     defaultValue = null
       // })
   // ) {

   // }
}

fun NavGraphBuilder.mealRoute(){
   // composable(route = Screen.Meal.route) {

    //}
}

fun NavGraphBuilder.barcodeRoute(){
    composable(route = Screen.Barcode.route) {

    }
}