package com.example.api_receitas.features.details.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_receitas.data.model.receita.requisicao.IngredienteRequisicao
import com.example.api_receitas.data.model.receita.requisicao.PassoRequisicao
import com.example.api_receitas.data.model.receita.requisicao.ReceitaRequisicao
import com.example.api_receitas.data.model.receita.resposta.ReceitaResposta
import com.example.api_receitas.data.network.receita.ReceitaApiService
import com.example.api_receitas.data.network.usuario.UsuarioApiService
import kotlinx.coroutines.launch

class ReceitaViewModel: ViewModel() {

    var receita by mutableStateOf<ReceitaResposta?>(null)
    var carregando by mutableStateOf(false)
    var mensagemFeedback by mutableStateOf("")
    var listaReceitas by mutableStateOf<List<ReceitaResposta>>(emptyList())
    var filtroAtual by mutableStateOf("Todos")

    fun buscaReceitaPorId(id: Long) {
        viewModelScope.launch {
            carregando = true
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
                carregando = false
            }
        }
    }

    fun buscarTodasAsReceitas(){
        viewModelScope.launch {
            carregando = true
            try{
                val resposta = ReceitaApiService.RetrofitClient.apiService.ListarTodasAsReceitas()
                if(resposta.isSuccessful){
                    listaReceitas = resposta.body() ?: emptyList()
                }else{
                    mensagemFeedback = "Não foi possível carregar a api ${resposta.code()}"
                }

            }catch (e:Exception){
                mensagemFeedback = "Não foi possível se conectar a api ${e.message}"

            }finally {
                carregando = false
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
    fun CriarNovaReceita(nome: String,
                         descricao: String,
                         tempoPreparo: Double,
                         porcoes: Double,
                         ingredientes: List<IngredienteRequisicao>,
                         passos: List<PassoRequisicao>,
                         onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
            val novaReceita = ReceitaRequisicao(
                nome = nome,
                descricao = descricao,
                tempoPreparo = tempoPreparo,
                porcoes = porcoes,
                ingredientes = ingredientes,
                passos = passos
            )
            val resposta = ReceitaApiService.RetrofitClient.apiService.AdicionarReceita(novaReceita)
                if (resposta.isSuccessful) {
                    onSuccess()
                } else {
                    val erroDaApi = resposta.errorBody()?.string() ?: "Erro desconhecido"
                    mensagemFeedback = "Erro ${resposta.code()}: $erroDaApi"
                }
            }catch (e: Exception){
                e.message
            }



        }

    }

    fun atualizarReceita(id: Long, receitaAtualizada: ReceitaResposta, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.AtualizarReceita(id, receitaAtualizada)
                if (resposta.isSuccessful) {
                    onSuccess()
                } else {
                    mensagemFeedback = "Erro ao atualizar receita: ${resposta.code()}"
                }
            } catch (e: Exception) {
                mensagemFeedback = "Erro de conexão: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

    fun deletarReceita(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.DeletarReceita(id)
                if (resposta.isSuccessful) {
                    onSuccess()
                } else {
                    mensagemFeedback = "Erro ao deletar receita: ${resposta.code()}"
                }
            } catch (e: Exception) {
                mensagemFeedback = "Erro de conexão: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }
}