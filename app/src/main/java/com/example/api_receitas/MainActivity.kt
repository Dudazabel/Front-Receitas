package com.example.api_receitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.api_receitas.features.home.ui.InitialScreenLogo
import com.example.api_receitas.features.onboarding.ui.OnboardingScreen
import com.example.api_receitas.ui.theme.APIReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIReceitasTheme {

            }
        }
    }
}
