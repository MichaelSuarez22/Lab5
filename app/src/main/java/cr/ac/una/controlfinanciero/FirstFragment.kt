package cr.ac.una.controlfinanciero

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import cr.ac.una.controlfinanciero.adapter.MovimientoAdapter
import cr.ac.una.controlfinanciero.controller.CrearMovimiento
import cr.ac.una.controlfinanciero.controller.MovimientoController
import cr.ac.una.controlfinanciero.entity.Movimiento
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {

    private lateinit var adapter: MovimientoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val botonNuevo = view.findViewById<Button>(R.id.botonNuevo)
        botonNuevo.setOnClickListener {
            val intent = Intent(activity, CrearMovimiento::class.java)
            activity?.startActivityForResult(intent, REQUEST_CODE_CREAR_MOVIMIENTO)
        }

        val listaMovimientos = view.findViewById<ListView>(R.id.listaMovimientos)
        adapter = MovimientoAdapter(requireContext(), MovimientoController.movimientos)
        listaMovimientos.adapter = adapter
        actualizarListaMovimientos()

        listaMovimientos.setOnItemClickListener { parent, view, position, id ->
            val movimiento = adapter.getItem(position)
            val intent = Intent(activity, CrearMovimiento::class.java)
            intent.putExtra("fecha", movimiento?.fecha)
            intent.putExtra("tipo", movimiento?.tipo)
            intent.putExtra("monto", movimiento?.monto.toString())
            intent.putExtra("index", position)
            activity?.startActivityForResult(intent, REQUEST_CODE_EDITAR_MOVIMIENTO)
        }

        listaMovimientos.setOnItemLongClickListener { parent, view, position, id ->
            mostrarDialogoConfirmacion(position)
            true
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        actualizarListaMovimientos()
    }

    public fun actualizarListaMovimientos() {
        adapter.notifyDataSetChanged()
    }

    private fun agregarMovimiento(movimiento: Movimiento) {
        CoroutineScope(Dispatchers.Main).launch {
            MovimientoController.insertMovimiento(movimiento)
            actualizarListaMovimientos()
        }
    }

    fun eliminarMovimiento(position: Int) {
        MovimientoController.deleteMovimiento(position)
        actualizarListaMovimientos()
    }

    private fun mostrarDialogoConfirmacion(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar Movimiento")
            .setMessage("¿Estás seguro de que deseas eliminar este movimiento?")
            .setPositiveButton("Eliminar", DialogInterface.OnClickListener { dialog, which ->
                eliminarMovimiento(position)
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    companion object {
        const val REQUEST_CODE_CREAR_MOVIMIENTO = 1
        const val REQUEST_CODE_EDITAR_MOVIMIENTO = 2
    }
}



// FirstFragment.kt

