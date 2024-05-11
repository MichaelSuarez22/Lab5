package cr.ac.una.controlfinanciero.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cr.ac.una.controlfinanciero.DAO.MovimientoDAO
import cr.ac.una.controlfinanciero.entity.Movimiento

@Database(entities = [Movimiento::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movimientoDao(): MovimientoDAO

    companion object {
        // Instancia única de la base de datos
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método estático para obtener la instancia única de la base de datos
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "control_financiero_database" // Nombre de la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
