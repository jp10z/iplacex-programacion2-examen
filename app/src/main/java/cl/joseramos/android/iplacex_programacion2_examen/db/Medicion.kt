package cl.joseramos.android.iplacex_programacion2_examen.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val valor: Int,
    val fecha: LocalDate,
    val tipo: String
)

enum class TipoMedidor {
    AGUA,
    LUZ,
    GAS
}
