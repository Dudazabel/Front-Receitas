package com.example.api_receitas.data.model.receita.requisicao

data class ReceitaRequisicao (
    val nome: String,
    val descricao: String,
    val tempoPreparo: Double,
    val porcoes: Double,
    val ingredientes: List<IngredienteRequisicao>,
    val passos: List<PassoRequisicao>)