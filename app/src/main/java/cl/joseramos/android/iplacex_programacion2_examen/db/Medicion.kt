package cl.joseramos.android.iplacex_programacion2_examen.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Id, autoincremental
    val valor: Int, // Utilizo int, se puede considerar cambiar a Long si es que se requiere ingresar numeros más grandes
    val fecha: LocalDate,
    val tipo: String
)

// Lo utilizo para que sea más fácil usar y obtener los tipos de medidores disponibles
enum class TipoMedidor {
    AGUA,
    LUZ,
    GAS
}
