package com.example.api_receitas.features.details.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_receitas.data.model.receita.ReceitaResposta
import com.example.api_receitas.data.network.receita.ReceitaApiService
import com.example.api_receitas.data.network.usuario.UsuarioApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ReceitaViewModel: ViewModel() {

        var receita by mutableStateOf<ReceitaResposta?>(null)
        var EstaLogado by mutableStateOf(true)
        var mensagemFeedback by mutableStateOf("")

    fun buscaReceitaPorId(id: Long) {
        viewModelScope.launch {
            EstaLogado = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.buscarReceitaPorId(id)

                if (resposta.isSuccessful) {
                    receita = resposta.body()
                } else {
                    mensagemFeedback = "Erro ao buscar receita: Código ${resposta.code()}"
                }

            } catch (e: Exception) {
                mensagemFeedback = "Ocorreu uma falha no carregamento: ${e.message}"
            } finally {
                EstaLogado = false
            }
        }
    }


}