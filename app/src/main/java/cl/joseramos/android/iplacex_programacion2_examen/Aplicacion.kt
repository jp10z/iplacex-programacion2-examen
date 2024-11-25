package cl.joseramos.android.iplacex_programacion2_examen

import android.app.Application
import androidx.room.Room
import cl.joseramos.android.iplacex_programacion2_examen.db.BaseDatos

// Esto nos sirve para que sea más sencillo implementar el LazyColumn y el acceso a los datos
// mediante las corutinas, esto fue una recomendación propuesta pos los videos de la asignatura
class Aplicacion: Application() {
    val db by lazy { Room.databaseBuilder(this, BaseDatos::class.java, "mediciones.db").build() }
    val medicionDao by lazy { db.medicionDao() }
}