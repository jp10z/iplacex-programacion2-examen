package cl.joseramos.android.iplacex_programacion2_examen

import android.app.Application
import androidx.room.Room
import cl.joseramos.android.iplacex_programacion2_examen.db.BaseDatos

class Aplicacion: Application() {
    val db by lazy { Room.databaseBuilder(this, BaseDatos::class.java, "mediciones.db").build() }
    val medicionDao by lazy { db.medicionDao() }
}