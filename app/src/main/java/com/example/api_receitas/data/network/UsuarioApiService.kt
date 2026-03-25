import com.example.api_receitas.data.model.UsuarioRequisicao
import com.example.api_receitas.data.model.UsuarioResposta
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface UsuarioApiService {

    @GET("usuario/{email}")
    suspend fun buscarUsuarioPorEmail(@Path("email") email: String): UsuarioResposta

    @POST("usuario")
    suspend fun adicionarUsuario(@Body usuario: UsuarioRequisicao):UsuarioResposta


    object RetrofitClient{
        private const val BASE_URL = "http://10.0.2.2:8081/"

        val apiService: UsuarioApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioApiService::class.java)
        }
    }
}