package com.example.api_receitas.features.details.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_receitas.data.model.receita.ReceitaResposta
import com.example.api_receitas.data.network.receita.ReceitaApiService
import kotlinx.coroutines.launch

class ReceitaViewModel: ViewModel() {

        var receita by mutableStateOf<ReceitaResposta?>(null)
        var estaLogado by mutableStateOf(true)
        var mensagemFeedback by mutableStateOf("")
        var listaReceitas by mutableStateOf<List<ReceitaResposta>>(emptyList())
    fun buscaReceitaPorId(id: Long) {
        viewModelScope.launch {
            estaLogado = true
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
                estaLogado = false
            }
        }
    }
    fun buscarTodasAsReceitas(){
        viewModelScope.launch {
            estaLogado = true
            try{
                val resposta = ReceitaApiService.RetrofitClient.apiService.ListarTodasAsReceitas()
                if(resposta.isSuccessful){
                    listaReceitas = resposta.body() ?: emptyList()
                }else{
                    mensagemFeedback = "Nao foi possivel carregar a api ${resposta.code()}"
                }

            }catch (e:Exception){
                mensagemFeedback = "Nao foi possivel se conectar a api ${e.message}"

            }finally {
                estaLogado = false;
            }
        }
    }


}