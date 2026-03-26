package com.example.api_receitas.data.model.usuario

import android.provider.ContactsContract.CommonDataKinds.Email

data class UsuarioRequisicao(
    val nome: String,
    val email: String,
    val senha: String
)
