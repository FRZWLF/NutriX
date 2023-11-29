package com.sem.nutrix.navigation

import com.sem.nutrix.util.Constants.ADD_SCREEN_ARGUMENT_KEY

sealed class Screen(val route: String) {
    object Authentication: Screen(route = "authentication_screen")
    object TermsAndConditions: Screen(route = "terms-and-conditions_screen")
    object Home: Screen(route = "home_screen")
    object ProductAdd: Screen(route = "Add_screen?$ADD_SCREEN_ARGUMENT_KEY=" +
            "{$ADD_SCREEN_ARGUMENT_KEY}") {
        fun passProductId(productId: String) =
            "add_screen?$ADD_SCREEN_ARGUMENT_KEY=$productId"
    }
    object MealProductList: Screen(route = "meal_screen")
    object Barcode: Screen(route = "barcode_screen")
}