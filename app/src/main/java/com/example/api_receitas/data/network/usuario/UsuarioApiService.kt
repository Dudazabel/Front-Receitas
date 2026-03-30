package com.example.api_receitas.data.network.usuario

import com.example.api_receitas.data.model.login.LoginRequisicao
import com.example.api_receitas.data.model.usuario.UsuarioRequisicao
import com.example.api_receitas.data.model.usuario.UsuarioResposta
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface UsuarioApiService {

    @POST("usuario")
    suspend fun adicionarUsuario(@Body usuario: UsuarioRequisicao): Response<UsuarioResposta>


    object RetrofitClient{
        private const val BASE_URL = "https://api-receitas-pb3e.onrender.com/"

        val apiService: UsuarioApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioApiService::class.java)
        }
    }

    @POST("/usuario/login")
    suspend fun login(
        @Body requisicao: LoginRequisicao
    ): UsuarioResposta
}