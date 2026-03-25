package com.example.api_receitas.features.authentication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text


class AuthenticationScreen {

    data class Usuario(
        val email: String,
        val senha: String
    )

    @Composable
    fun AuthenticationScreenLogIn(modifier: Modifier = Modifier){
        Column {
            Text(text = "Log In")
            Text(text = "Faça Log In a uma conta existente!")
        }

        Column {
            var email by remember { mutableStateOf("") }
            var senha by remember { mutableStateOf("") }
        }
    }

}