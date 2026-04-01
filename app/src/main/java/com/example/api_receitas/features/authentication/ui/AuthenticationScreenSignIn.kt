package com.example.api_receitas.features.authentication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_receitas.R
import com.example.api_receitas.data.viewmodel.AuthViewModel
import com.example.api_receitas.ui.theme.AzulFundo
import com.example.api_receitas.ui.theme.Laranja

@Composable
fun AuthenticationSignIn(
    viewModel: AuthViewModel = AuthViewModel(),
    onNavigateToLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulFundo)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    fontSize = 27.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Faça o cadastro para utilizar o app!",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 300.dp),
            shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var nome by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var senha by remember { mutableStateOf("") }
                var confirmarsenha by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Digite o seu nome: ") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Digite o seu email: ") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                var passwordVisibleS by remember { mutableStateOf(false) }
                var passwordVisibleC by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Digite a sua senha: ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisibleS) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisibleS)
                            painterResource(id = R.drawable.view)
                        else
                            painterResource(id = R.drawable.hide)
                        val description = if(passwordVisibleS) "Ocultar senha" else "Mostrar senha"

                        IconButton(onClick = { passwordVisibleS = !passwordVisibleS }) {
                            Icon(
                                painter = image,
                                contentDescription = description,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = confirmarsenha,
                    onValueChange = { confirmarsenha = it },
                    label = { Text("Confirme a sua senha: ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisibleC) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisibleC)
                            painterResource(id = R.drawable.view)
                        else
                            painterResource(id = R.drawable.hide)
                        val description = if(passwordVisibleC) "Ocultar senha" else "Mostrar senha"

                        IconButton(onClick = { passwordVisibleC = !passwordVisibleC }) {
                            Icon(
                                painter = image,
                                contentDescription = description,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))


                if (viewModel.mensagemFeedback.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagemFeedback,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (senha == confirmarsenha && senha.isNotBlank()) {
                            viewModel.cadastrarUsuario(nome, email, senha) {
                                onNavigateToLogin()
                            }
                        } else {
                            viewModel.mensagemFeedback = "As senhas não coincidem ou estão vazias!"
                        }
                    },
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Laranja),
                    enabled = !viewModel.estaLogado
                ) {
                    Text(
                        text = if (viewModel.estaLogado) "Aguarde" else "Sign In",
                        fontSize = 16.sp
                    )
                }

            }
        }
    }
}