package cr.ac.una.controlfinanciero.service

import com.google.gson.GsonBuilder
import cr.ac.una.controlfinanciero.AuthInterceptor
import cr.ac.una.controlfinanciero.DAO.MovimientoDAO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovimientoService {
    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor("ijBzDL17YosVMAgwX_9KRy1En34aFcZhKoCe5rLvIGX4L136sA"))
        .build()

    val gson = GsonBuilder().setPrettyPrinting().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://crudapi.co.uk/api/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(MovimientoDAO::class.java)
}
