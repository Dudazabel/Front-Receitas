package com.example.api_receitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.api_receitas.navigation.AppNavigation


import com.example.api_receitas.ui.theme.APIReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIReceitasTheme {
                AppNavigation()
            }
        }
    }
}