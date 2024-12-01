import com.example.tb.ViewModel.ApiResponse
import com.example.tb.model.MyDataModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET




object RetrofitInstance {

        const val BASE_URL = "http://10.0.2.2:3000"

    val api: ApiService by lazy {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


interface ApiService {
    @GET("api/data")
    suspend fun getData(): Response<ApiResponse> // Ubah ke ApiResponse
}

