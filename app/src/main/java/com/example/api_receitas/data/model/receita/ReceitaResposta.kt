package com.example.api_receitas.data.model.receita

import com.google.gson.annotations.SerializedName

data class ReceitaResposta(val id: Long,
                           val nome: String,
                           val descricao: String,
                           val tempoPreparo: Double,
                           val porcoes: Double,
                           @SerializedName("ingredienteRespostaDtoList")
                           val ingredientes: List<IngredienteResposta>,
                           @SerializedName("PassoRepostaDto")
                           val passos: List<PassoResposta>)
