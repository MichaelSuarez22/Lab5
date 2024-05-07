package cr.ac.una.controlfinanciero


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import cr.ac.una.controlfinanciero.controller.CrearMovimiento
import cr.ac.una.controlfinanciero.controller.MovimientoController
import cr.ac.una.controlfinanciero.entity.Movimiento

class MainActivity : AppCompatActivity() {

    private lateinit var movimientoController: MovimientoController

    private val firstFragment = FirstFragment()
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout

    companion object {
        const val REQUEST_CODE_CREAR_MOVIMIENTO = 1
        const val REQUEST_CODE_EDITAR_MOVIMIENTO = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        movimientoController = MovimientoController

        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Configurar el primer fragmento
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, firstFragment)
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Método para abrir SecondFragment desde el menú de navegación
    private fun abrirSecondFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        transaction.addToBackStack(null)
        transaction.commit()
    }

    // Método para agregar movimiento desde SecondFragment
    suspend fun agregarMovimiento(movimiento: Movimiento) {
        insertarMovimiento(movimiento)
        // No necesitas regresar al primer fragmento, ya que MainActivity permanece en segundo plano.
        // firstFragment.actualizarListaMovimientos()
    }

    // Método para insertar un movimiento en el controlador
    private suspend fun insertarMovimiento(movimiento: Movimiento) {
        movimientoController.insertMovimiento(movimiento)
        // Inicia la actividad CrearMovimiento
        val intent = Intent(this, CrearMovimiento::class.java)
        startActivityForResult(intent, REQUEST_CODE_CREAR_MOVIMIENTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CREAR_MOVIMIENTO -> {
                    val deletedIndex = data?.getIntExtra("deleted_index", -1)
                    if (deletedIndex != -1) {
                        deletedIndex?.let { firstFragment.eliminarMovimiento(it) }
                    }
                    firstFragment.actualizarListaMovimientos()
                }
                REQUEST_CODE_EDITAR_MOVIMIENTO -> {
                    val index = data?.getIntExtra("index", -1)
                    if (index != null && index != -1) {
                        val movimiento = obtenerMovimientoFromIntent(data)
                        if (movimiento != null) {
                            MovimientoController.updateMovimiento(index, movimiento)
                            firstFragment.actualizarListaMovimientos()
                        }
                    }
                }
            }
        }
    }

    private fun obtenerMovimientoFromIntent(data: Intent?): Movimiento? {
        val fecha = data?.getStringExtra("fecha")
        val tipo = data?.getIntExtra("tipo", 1)
        val monto = data?.getStringExtra("monto")

        return if (fecha != null && tipo != null && monto != null) {
            val montoDouble = monto.toDouble()
            Movimiento(null,montoDouble, tipo, fecha)
        } else {
            null
        }
    }
}




