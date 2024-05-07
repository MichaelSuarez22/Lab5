package cr.ac.una.controlfinanciero.entity

import java.io.Serializable

data class Movimiento(var _uuid :String?,var monto: Double, var tipo: Int, var fecha: String) : Serializable




