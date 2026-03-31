package com.example.api_receitas.data.model.receita.resposta

import com.example.api_receitas.data.model.receita.resposta.IngredienteResposta
import com.example.api_receitas.data.model.receita.resposta.PassoResposta
import com.google.gson.annotations.SerializedName

data class ReceitaResposta(
    val id: Long,
    val nome: String,
    val descricao: String,
    val tempoPreparo: Double,
    val porcoes: Double,
    @SerializedName("ingredientes")
    val ingredientes: List<IngredienteResposta>?,
    @SerializedName("passos")
    val passos: List<PassoResposta>?,
    val foto: String? = null
)