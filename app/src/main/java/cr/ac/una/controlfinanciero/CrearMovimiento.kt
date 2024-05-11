
package cr.ac.una.controlfinanciero.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cr.ac.una.controlfinanciero.R
import cr.ac.una.controlfinanciero.db.AppDatabase
import cr.ac.una.controlfinanciero.entity.Movimiento
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class CrearMovimiento : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_movimiento)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val editTextMonto = findViewById<EditText>(R.id.editTextMonto)
        val botonInsertar = findViewById<Button>(R.id.botonInsertar)
        val botonSalir = findViewById<Button>(R.id.botonSalir)

        // Configurar el selector de fecha para que muestre la fecha actual
        val cal = Calendar.getInstance()
        datePicker.init(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
            null
        )

        // Configurar el spinner con los valores de tipo
        val tipos = arrayOf("Crédito", "Débito")

        botonInsertar.setOnClickListener {
            mostrarDialogoConfirmacion()
        }

        botonSalir.setOnClickListener {
            setResultAndFinish(Activity.RESULT_CANCELED)
        }
    }

    private fun mostrarDialogoConfirmacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
            .setMessage("¿Estás seguro de que deseas insertar el registro?")
            .setPositiveButton("Sí") { dialog, which ->
                GlobalScope.launch {
                    insertarRegistro()
                }
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private suspend fun insertarRegistro() {
        val movimiento = obtenerMovimientoFromUI()
        if (movimiento != null) {
            // Insertar el movimiento en la base de datos utilizando el DAO
            try {
                AppDatabase.getInstance(this).movimientoDao().insertMovimiento(movimiento)
                setResultAndFinish(Activity.RESULT_OK)
            } catch (e: Exception) {
                e.printStackTrace()
                // Manejar cualquier error que pueda ocurrir durante la inserción
            }
        }
    }

    private fun obtenerMovimientoFromUI(): Movimiento? {
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val editTextMonto = findViewById<EditText>(R.id.editTextMonto)

        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth
        val fecha = "$year-${month + 1}-$day" // Formato YYYY-MM-DD

        val tipo = if (spinnerTipo.selectedItemPosition == 0) 1 else 2
        val montoString = editTextMonto.text.toString()

        return if (montoString.matches("^\\d+(\\.\\d{1,2})?\$".toRegex())) {
            val monto = montoString.toDouble()
            Movimiento(null, monto, tipo, fecha)
        } else {
            null
        }
    }

    private fun setResultAndFinish(resultCode: Int) {
        val intent = Intent()
        setResult(resultCode, intent)
        finish()
    }
}


