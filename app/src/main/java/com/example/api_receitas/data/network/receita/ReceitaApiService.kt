package com.example.api_receitas.data.network.receita

import com.example.api_receitas.data.model.receita.ReceitaResposta
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ReceitaApiService {

    @GET("receita/{id}")
    suspend fun buscarReceitaPorId(@Path("id") id: Long): Response<ReceitaResposta>
    @GET("receita")
    suspend fun ListarTodasAsReceitas(): Response<List<ReceitaResposta>>
    @GET("receita/filtro/tempo")
    suspend fun filtrarReceitasPorTempo(
        @Query("tempoMin") min: Double,
        @Query("tempoMax") max: Double
    ): Response<List<ReceitaResposta>>

    @GET("receita/porcao/filtro")
    suspend fun FiltrarPorPorcao(
        @Query("min") min: Double,
        @Query("max") max: Double):Response<List<ReceitaResposta>>



    object RetrofitClient {
            private const val BASE_URL = "https://api-receitas-pb3e.onrender.com/"


        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val apiService: ReceitaApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReceitaApiService::class.java)
        }
    }
}