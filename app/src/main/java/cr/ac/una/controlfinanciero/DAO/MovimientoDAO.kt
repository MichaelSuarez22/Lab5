package cr.ac.una.controlfinanciero.DAO

import cr.ac.una.controlfinanciero.entity.Movimiento
import cr.ac.una.controlfinanciero.entity.Movimientos
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovimientoDAO {

    @GET("movimiento")
    suspend fun getItems(): Movimientos

    @GET("movimiento/{uuid}")
    suspend fun getItem(@Path("uuid") uuid: String): Movimiento

    @POST("movimiento")
    suspend fun createItem( @Body items: List<Movimiento>): Movimientos

    @PUT("movimiento/{uuid}")
    suspend fun updateItem(@Path("uuid") uuid: String, @Body item: Movimiento): Movimiento

    @DELETE("movimiento/{uuid}")
    suspend fun deleteItem(@Path("uuid") uuid: String)
}