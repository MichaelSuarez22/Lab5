package cr.ac.una.controlfinanciero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cr.ac.una.controlfinanciero.entity.Movimiento
import java.text.SimpleDateFormat
import java.util.*

class DetalleMovimiento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_movimiento)

        val movimiento = intent.getSerializableExtra("movimiento") as Movimiento

        val tvFecha = findViewById<TextView>(R.id.tvFecha)
        val tvTipo = findViewById<TextView>(R.id.tvTipo)
        val tvMonto = findViewById<TextView>(R.id.tvMonto)

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val date = dateFormat.parse(movimiento.fecha)
        val fecha = dateFormat.format(date)

        tvFecha.text = "Fecha: $fecha"
        tvTipo.text = "Tipo: ${if (movimiento.tipo == 1) "Crédito" else "Débito"}"
        tvMonto.text = "Monto: ${movimiento.monto}"
    }
}
