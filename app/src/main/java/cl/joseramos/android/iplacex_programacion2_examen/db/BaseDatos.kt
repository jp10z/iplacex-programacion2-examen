package cl.joseramos.android.iplacex_programacion2_examen.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Medicion::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class BaseDatos: RoomDatabase() {
    abstract fun medicionDao():MedicionDao

    companion object {
        @Volatile
        private var instance: BaseDatos? = null

        fun getInstance(contexto: Context): BaseDatos {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDatos::class.java,
                    "mediciones.db"
                ).build()
            }.also {
                instance = it
            }
        }
    }
}