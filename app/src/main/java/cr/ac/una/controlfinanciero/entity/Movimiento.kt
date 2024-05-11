package cr.ac.una.controlfinanciero.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movimiento") // Nombre de la tabla en la base de datos
data class Movimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null, // Clave primaria, puedes cambiar el tipo según tu necesidad
    val monto: Double,
    val tipo: Int,
    val fecha: String
) : Serializable
