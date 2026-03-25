package com.example.api_receitas.features.authentication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_receitas.data.model.UsuarioRequisicao
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthViewModel: ViewModel() {

    var EstaLogado by mutableStateOf(false)
    var mensagemFeedback by mutableStateOf("")


    fun cadastrarUsuario(nome: String, email: String, senha: String, onSuccess: () -> Unit){
        viewModelScope.launch {
            EstaLogado = true
            mensagemFeedback = ""
            try {
                val novoUsuario = UsuarioRequisicao(nome = nome, email = email , senha = senha)
                UsuarioApiService.RetrofitClient.apiService.adicionarUsuario(novoUsuario)
                mensagemFeedback = "Conta criada com sucesso"
                onSuccess()
            }catch (e: Exception){
                mensagemFeedback = "Houve algum erro ao cadastrar tente novamente"
            }finally {
                EstaLogado = false
            }
        }
    }


    fun fazerLogin(email: String, senha: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            EstaLogado = true
            mensagemFeedback = ""
            try {
                val usuario = UsuarioApiService.RetrofitClient.apiService.buscarUsuarioPorEmail(email)

                if (usuario != null) {
                    mensagemFeedback = "Login realizado com sucesso!"
                    onSuccess()
                }
            } catch (e: Exception) {
                mensagemFeedback = "Usuário não encontrado"
            } finally {
                EstaLogado = false
            }
        }
    }



}


