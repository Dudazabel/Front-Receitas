package com.example.api_receitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.api_receitas.features.authentication.ui.AuthenticationLogIn
import com.example.api_receitas.features.authentication.ui.AuthenticationSignIn
import com.example.api_receitas.features.details.ui.RecipeDetailScreen
import com.example.api_receitas.ui.theme.APIReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIReceitasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    RecipeDetailScreen(
                        receitaId = 1L,
                        onVoltarClick = {

                            println("Usuário clicou em voltar")
                        }
                    )
                }
            }
        }
    }
}