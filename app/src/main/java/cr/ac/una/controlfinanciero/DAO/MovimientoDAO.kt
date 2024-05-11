package cr.ac.una.controlfinanciero.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cr.ac.una.controlfinanciero.entity.Movimiento


@Dao
interface MovimientoDAO {
    @Query("SELECT * FROM movimiento")
    suspend fun getAllMovimientos(): List<Movimiento>

    @Insert
    suspend fun insertMovimiento(movimiento: Movimiento)

    @Update
    suspend fun updateMovimiento(movimiento: Movimiento)

    @Delete
    suspend fun deleteMovimiento(movimiento: Movimiento)
}