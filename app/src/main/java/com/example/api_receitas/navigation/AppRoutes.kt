package com.example.api_receitas.navigation

sealed class Screen(val route: String){
    data object Initial : Screen("initial_screen")
    data object Onboarding : Screen("onboarding_screen")

    data object Login : Screen("login_screen")
    data object SingIn : Screen("singin_screen")

    data object Home : Screen("home_screen")
    data object Create : Screen("create_screen")

    data object Confirmation : Screen("confirmation_screen/{acao}"){
        fun createRoute(acao: String) = "confirmation_screen/$acao"
    }

    data object Details : Screen("details_screen/{recipeId}"){
        fun createRoute(recipeId: Long) = "details_screen/$recipeId"
    }
    data object Edit : Screen("edit_screen/{recipeId}"){
        fun createRoute(recipeId: Long) = "edit_screen/$recipeId"
    }
}