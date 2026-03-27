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
        var EstaLogado by mutableStateOf(true)
        var mensagemFeedback by mutableStateOf("")
        var listaReceitas by mutableStateOf<List<ReceitaResposta>>(emptyList())
        var filtroAtual by mutableStateOf("Todos")
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
    fun buscarTodasAsReceitas(){
        viewModelScope.launch {
            EstaLogado = true
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
                EstaLogado = false;
            }
        }
    }

    fun filtrarReceitasPorTempo(min:Double, max:Double){
        viewModelScope.launch {
            EstaLogado = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.filtrarReceitasPorTempo(min, max)
                if(resposta.isSuccessful){
                    listaReceitas = resposta.body() ?: emptyList()
                }else{
                    mensagemFeedback = "Nao foi encontrado nenhuma receita nesse filtro ${resposta.code()}"
                }
            }catch (e:Exception){
                mensagemFeedback = "Nao foi possivel se conectar a api ${e.message}"
            }finally {
                EstaLogado = false
            }
        }
    }
    fun filtrarPorPorcoes(min:Double, max:Double){
        viewModelScope.launch {
            EstaLogado = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.FiltrarPorPorcao(min, max)
                if(resposta.isSuccessful){
                    listaReceitas = resposta.body() ?: emptyList()
                }else{
                    mensagemFeedback = "Nao foi encontrada nenhuma receita com esse filtro ${resposta.code()}"
                }
            }catch (e:Exception){
                mensagemFeedback = "Nao foi possivel se conectar a api ${e.message}"
            }finally {
                EstaLogado = false
            }
        }
    }


}