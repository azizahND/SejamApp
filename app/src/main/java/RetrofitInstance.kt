import com.example.tb.model.MyDataModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import androidx.tracing.perfetto.handshake.protocol.Response as Response1

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:3000" // IP untuk emulator

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("endpoint")
    suspend fun getData(): Response<List<MyDataModel>>
}
