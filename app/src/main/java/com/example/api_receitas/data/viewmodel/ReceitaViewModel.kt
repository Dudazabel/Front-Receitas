package com.example.api_receitas.data.viewmodel

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReceitaViewModel: ViewModel() {

    var receita by mutableStateOf<ReceitaResposta?>(null)
    var carregando by mutableStateOf(false)
    var mensagemFeedback by mutableStateOf("")
    var listaReceitas by mutableStateOf<List<ReceitaResposta>>(emptyList())
    var filtroAtual by mutableStateOf("Todos")
    var searchJob: Job? = null
    var ListaDeIngrediente by mutableStateOf<List<String>>(emptyList())
    var ingredienteSelecionado by mutableStateOf("Todos")

    private fun tratarErroApi(codigo: Int, corpoErro: String?): String {
        return when (codigo) {
            400 -> "Dados inválidos. Verifique se preencheu todos os campos corretamente."
            401 -> "Sessão expirada. Por favor, faça login novamente."
            404 -> "Receita não encontrada."
            500 -> "Erro no servidor. Tente novamente mais tarde."
            else -> "Ocorreu um erro inesperado (Código: $codigo)"
        }
    }

    private fun tratarExcecao(e: Exception): String {
        return when (e) {
            is java.net.UnknownHostException,
            is java.net.ConnectException -> "Sem conexão com a internet."
            is java.net.SocketTimeoutException -> "O servidor demorou muito a responder."
            is retrofit2.HttpException -> "Erro inesperado no servidor."
            else -> "Não foi possível completar a ação. Tente novamente."
        }
    }

    fun buscaReceitaPorId(id: Long) {
        viewModelScope.launch {
            carregando = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.buscarReceitaPorId(id)

                if (resposta.isSuccessful) {
                    receita = resposta.body()
                } else {
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            } catch (e: Exception) {
                mensagemFeedback = tratarExcecao(e)
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
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            }catch (e:Exception){
                mensagemFeedback = tratarExcecao(e)
            }finally {
                carregando = false
            }
        }
    }
     fun realizarBuscaGeral(texto: String) {
        if (texto.isBlank()) {
            buscarTodasAsReceitas()
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            kotlinx.coroutines.delay(500)

            try {
                val resNome = ReceitaApiService.RetrofitClient.apiService.buscarPorNome(texto)
                val resIngrediente = ReceitaApiService.RetrofitClient.apiService.buscarPorIngrediente(texto)

                val listaFinal = mutableListOf<ReceitaResposta>()

                if (resNome.isSuccessful) {
                    resNome.body()?.let { listaFinal.addAll(it) }
                }

                if (resIngrediente.isSuccessful) {
                    resIngrediente.body()?.let { listaIng ->
                        val idsExistentes = listaFinal.map { it.id }.toSet()
                        listaFinal.addAll(listaIng.filter { it.id !in idsExistentes })
                    }
                }
                listaReceitas = listaFinal

            } catch (e: Exception) {
                mensagemFeedback = tratarExcecao(e)
            }
        }
    }

    fun filtrarReceitasPorTempo(min:Double, max:Double){
        viewModelScope.launch {
            carregando = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.filtrarReceitasPorTempo(min, max)
                if(resposta.isSuccessful){
                    listaReceitas = resposta.body() ?: emptyList()
                }else{
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            }catch (e:Exception){
                mensagemFeedback = tratarExcecao(e)
            }finally {
                carregando = false
            }
        }
    }
    fun filtrarPorPorcoes(min:Double, max:Double){
        viewModelScope.launch {
            carregando = true
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.FiltrarPorPorcao(min, max)
                if(resposta.isSuccessful){
                    listaReceitas = resposta.body() ?: emptyList()
                }else{
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            }catch (e:Exception){
                mensagemFeedback = tratarExcecao(e)
            }finally {
                carregando = false
            }
        }
    }
    fun CriarNovaReceita(nome: String,
                         descricao: String,
                         tempoPreparo: Double,
                         porcoes: Double,
                         ingredientes: List<IngredienteRequisicao>,
                         passos: List<PassoRequisicao>,
                         foto: String?,
                         onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                val novaReceita = ReceitaRequisicao(
                    nome = nome,
                    descricao = descricao,
                    tempoPreparo = tempoPreparo,
                    porcoes = porcoes,
                    ingredientes = ingredientes,
                    passos = passos,
                    foto = foto
                )
                val resposta = ReceitaApiService.RetrofitClient.apiService.AdicionarReceita(novaReceita)
                if (resposta.isSuccessful) {
                    onSuccess()
                } else {
                    val erroDaApi = resposta.errorBody()?.string() ?: "Erro desconhecido"
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            }catch (e: Exception){
                mensagemFeedback = tratarExcecao(e)
            } finally {
                carregando = false
            }
        }
    }

    fun selecionarTodos(){
        ingredienteSelecionado = "Todos"
        buscarTodasAsReceitas()
    }

    fun selecionarIngredientes(ingredientes: String){
        ingredienteSelecionado = ingredientes
        realizarBuscaGeral(ingredientes)
    }

    fun buscarTodosOsIngredientes() {
        viewModelScope.launch {
            try {
                val resposta = ReceitaApiService.RetrofitClient.apiService.BuscarTodosIngrediente()

                if (resposta.isSuccessful) {
                    ListaDeIngrediente = resposta.body() ?: emptyList()
                } else if (resposta.code() == 204) {
                    ListaDeIngrediente = emptyList()
                } else {
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            } catch (e: Exception) {
                mensagemFeedback = tratarExcecao(e)
            }
        }
    }

    fun atualizarReceita(id: Long, receitaAtualizada: ReceitaRequisicao, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            try {
                android.util.Log.d("UPDATE_FOTO", "Foto sendo enviada: ${
                    if (receitaAtualizada.foto != null) "SIM (${receitaAtualizada.foto.take(50)}...)" else "NÃO (null)"
                }")

                val resposta = ReceitaApiService.RetrofitClient.apiService.AtualizarReceita(id, receitaAtualizada)
                if (resposta.isSuccessful) {
                    onSuccess()
                } else {
                    val erroDetalhado = resposta.errorBody()?.string() ?: "Erro desconhecido"
                    android.util.Log.e("API_ERROR", "Erro ${resposta.code()}: $erroDetalhado")
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            } catch (e: Exception) {
                mensagemFeedback = tratarExcecao(e)
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
                    mensagemFeedback = tratarErroApi(resposta.code(), resposta.errorBody()?.string())
                }
            } catch (e: Exception) {
                mensagemFeedback = tratarExcecao(e)
            } finally {
                carregando = false
            }
        }
    }
}