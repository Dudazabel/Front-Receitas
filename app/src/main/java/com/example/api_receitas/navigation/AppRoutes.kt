package com.example.api_receitas.navigation

sealed class Screen(val route: String){
    object Initial : Screen("initial_screen")
    object Onboarding : Screen("onboarding_screen")

    object Login : Screen("login_screen")
    object SingIn : Screen("singin_screen")

    object Home : Screen("home_screen")
    object Create : Screen("create_screen")

    object Details : Screen("details_screen/{recipeId}"){
        fun createRoute(recipeId: Long) = "details_screen/$recipeId"
    }
    object Edit : Screen("edit_screen/{recipeId}"){
        fun createRoute(recipeId: Long) = "edit_screen/$recipeId"
    }
}