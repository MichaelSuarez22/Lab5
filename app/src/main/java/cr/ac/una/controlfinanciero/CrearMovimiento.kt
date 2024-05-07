package cr.ac.una.controlfinanciero.controller

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cr.ac.una.controlfinanciero.R
import cr.ac.una.controlfinanciero.entity.Movimiento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CrearMovimiento : AppCompatActivity() {
    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_movimiento)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val editTextMonto = findViewById<EditText>(R.id.editTextMonto)
        val botonInsertar = findViewById<Button>(R.id.botonInsertar)
        val botonEliminar = findViewById<Button>(R.id.botonEliminar)
        val botonSalir = findViewById<Button>(R.id.botonSalir)

        val controller = MovimientoController

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
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapter

        // Obtener datos precargados del intent, si los hay
        val intent = intent
        if (intent != null && intent.hasExtra("fecha") && intent.hasExtra("tipo") && intent.hasExtra(
                "monto"
            )
        ) {
            val fecha = intent.getStringExtra("fecha")
            val tipo = intent.getIntExtra("tipo", 1)
            val monto = intent.getStringExtra("monto")
            index = intent.getIntExtra("index", -1)

            editTextMonto.setText(monto)

            // Establecer los valores precargados en las vistas correspondientes
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = dateFormat.parse(fecha)
            val calFecha = Calendar.getInstance()
            calFecha.time = parsedDate
            datePicker.init(
                calFecha.get(Calendar.YEAR),
                calFecha.get(Calendar.MONTH),
                calFecha.get(Calendar.DAY_OF_MONTH),
                null
            )
            spinnerTipo.setSelection(tipo - 1) // Restar 1 para adaptarse a los índices de la matriz
        }

        botonInsertar.setOnClickListener {
            mostrarDialogoConfirmacion()
        }

        botonEliminar.setOnClickListener {
            mostrarDialogoEliminar()
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
                GlobalScope.launch(Dispatchers.Main) {
                    insertarRegistro()
                }
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun mostrarDialogoEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Movimiento")
            .setMessage("¿Estás seguro de que deseas eliminar este movimiento?")
            .setPositiveButton("Eliminar", DialogInterface.OnClickListener { dialog, which ->
                eliminarMovimiento()
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private suspend fun insertarRegistro() {
        val movimiento = obtenerMovimientoFromUI()
        if (movimiento != null) {
            val controller = MovimientoController

            if (index != -1) {
                controller.updateMovimiento(index, movimiento)
                setResultAndFinish(Activity.RESULT_OK)
            } else {
                controller.insertMovimiento(movimiento)
                setResultAndFinish(Activity.RESULT_OK)
            }
        }
    }

    private fun eliminarMovimiento() {
        if (index != -1) {
            val controller = MovimientoController
            controller.deleteMovimiento(index)
            setResultAndFinish(Activity.RESULT_OK)
        } else {
            // No hay movimiento para eliminar
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
            Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun setResultAndFinish(resultCode: Int) {
        val intent = Intent()
        setResult(resultCode, intent)
        finish()
    }
}
