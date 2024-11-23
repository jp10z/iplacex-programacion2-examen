package cl.joseramos.android.iplacex_programacion2_examen.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val codigo: String,
    val fecha: LocalDate,
    val tipo: String
){

}
