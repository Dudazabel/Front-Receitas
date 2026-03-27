package com.example.api_receitas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api_receitas.features.authentication.ui.AuthenticationLogIn
import com.example.api_receitas.features.authentication.ui.AuthenticationSignIn
import com.example.api_receitas.features.create.ui.CreateRecipe
import com.example.api_receitas.features.details.ui.RecipeDetailScreen
import com.example.api_receitas.features.edit.ui.RecipeEditScreen
import com.example.api_receitas.features.home.ui.HomeScreen
import com.example.api_receitas.features.home.ui.InitialScreenLogo
import com.example.api_receitas.features.onboarding.ui.OnboardingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Initial.route
    ) {
        composable(Screen.Initial.route) {
            InitialScreenLogo(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route)
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Login.route) {
            AuthenticationLogIn(
                onNavigateToHome = { nomeUsuario ->
                    navController.navigate("${Screen.Home.route}/$nomeUsuario")
                },
                onNavigateToSignUp = { navController.navigate(Screen.SingIn.route) }
            )
        }
        composable(Screen.SingIn.route) {
            AuthenticationSignIn(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "${Screen.Home.route}/{nomeUsuario}",
            arguments = listOf(navArgument("nomeUsuario") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val nomeUsuarioRecebido = backStackEntry.arguments?.getString("nomeUsuario") ?: "Usuário"

            HomeScreen(
                nomeUsuario = nomeUsuarioRecebido,
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.Details.createRoute(recipeId))
                },
                onAddRecipeClick = { navController.navigate(Screen.Create.route) }
            )
        }

        composable(Screen.Create.route) {
            CreateRecipe(
                onBackClick = { navController.popBackStack() },
                onRecipeSaved = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("recipeId"){
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L

            RecipeDetailScreen(
                receitaId = recipeId,
                onVoltarClick = { navController.popBackStack() },
                onEditClick = { navController.navigate(Screen.Edit.createRoute(recipeId)) }
            )
        }
        composable(
            route = Screen.Edit.route,
            arguments = listOf(navArgument("recipeId"){
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L

            RecipeEditScreen(
                recipeId = recipeId,
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}