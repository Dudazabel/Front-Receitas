package com.example.api_receitas.features.authentication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_receitas.data.model.login.LoginRequisicao
import com.example.api_receitas.data.model.usuario.UsuarioRequisicao
import com.example.api_receitas.data.network.usuario.UsuarioApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthViewModel: ViewModel() {

    var estaLogado by mutableStateOf(false)
    var mensagemFeedback by mutableStateOf("")
    var nomeUsuarioLogado by mutableStateOf("")

    fun cadastrarUsuario(nome: String, email: String, senha: String, onSuccess: () -> Unit){
        viewModelScope.launch {
            estaLogado = true
            mensagemFeedback = ""
            try {
                val novoUsuario = UsuarioRequisicao(nome = nome, email = email , senha = senha)
                UsuarioApiService.RetrofitClient.apiService.adicionarUsuario(novoUsuario)
                mensagemFeedback = "Conta criada com sucesso"
                onSuccess()
            }catch (e: Exception){
                mensagemFeedback = "Houve algum erro ao cadastrar tente novamente"
            }finally {
                estaLogado = false
            }
        }
    }


    fun fazerLogin(email: String, senha: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            estaLogado = true
            mensagemFeedback = ""
            try {
                val resposta = UsuarioApiService.RetrofitClient.apiService.login(LoginRequisicao(email,senha))

                nomeUsuarioLogado = resposta.nome
                mensagemFeedback = "Login realizado com sucesso!"
                onSuccess()

            } catch (e: Exception) {
                mensagemFeedback = "Email ou senha inválidos"

            } finally {
                estaLogado = false
            }
        }
    }
}


