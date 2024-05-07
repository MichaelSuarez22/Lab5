

/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cr.ac.una.controlfinanciero.R
import cr.ac.una.controlfinanciero.entity.Movimiento

class MovimientoAdapter(context: Context, movimientos: List<Movimiento>) :
    ArrayAdapter<Movimiento>(context, 0, movimientos) {

    private var movimientosList: MutableList<Movimiento> = movimientos.toMutableList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val monto = view!!.findViewById<TextView>(R.id.monto)
        val tipo = view.findViewById<TextView>(R.id.tipo)
        val fecha = view.findViewById<TextView>(R.id.fecha)


        val movimiento = getItem(position)
        monto.text = movimiento?.monto.toString()
        tipo.text = if (movimiento?.tipo == 1) "Crédito" else "Débito"
        fecha.text = movimiento?.fecha.toString()


        return view
    }

    // Método para actualizar la lista de movimientos y notificar al adaptador sobre el cambio
    fun actualizarMovimientos(nuevosMovimientos: List<Movimiento>) {
        movimientosList.clear()
        movimientosList.addAll(nuevosMovimientos)
        notifyDataSetChanged()
    }
}
*/

package cr.ac.una.controlfinanciero.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cr.ac.una.controlfinanciero.R
import cr.ac.una.controlfinanciero.entity.Movimiento



class MovimientoAdapter(context: Context, movimientos: List<Movimiento>) :
    ArrayAdapter<Movimiento>(context, 0, movimientos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val movimiento = getItem(position)

        val fechaTextView = view!!.findViewById<TextView>(R.id.fecha)
        val tipoTextView = view.findViewById<TextView>(R.id.tipo)
        val montoTextView = view.findViewById<TextView>(R.id.monto)

        fechaTextView.text = movimiento!!.fecha.toString()
        tipoTextView.text = movimiento.tipo.toString()
        montoTextView.text = movimiento.monto.toString()

        return view
    }
}