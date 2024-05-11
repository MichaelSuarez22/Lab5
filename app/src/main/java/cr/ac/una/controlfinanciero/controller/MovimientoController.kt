package cr.ac.una.controlfinanciero.controller

import cr.ac.una.controlfinanciero.entity.Movimiento
import cr.ac.una.controlfinanciero.service.MovimientoService

object MovimientoController {
    var movimientoService= MovimientoService()
    var movimientos: ArrayList<Movimiento> = arrayListOf()

    suspend fun insertMovimiento(movimiento: Movimiento) {
        movimientos.add(movimiento) // Agregar el nuevo movimiento a la lista existente

    }

    fun deleteMovimiento(position: Int) {
        if (position >= 0 && position < movimientos.size) {
            movimientos.removeAt(position)
        }
    }

    fun updateMovimiento(index: Int, movimiento: Movimiento) {
        if (index >= 0 && index < movimientos.size) {
            movimientos[index] = movimiento
        }
    }
}
