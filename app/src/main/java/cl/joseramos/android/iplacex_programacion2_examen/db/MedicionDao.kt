package cl.joseramos.android.iplacex_programacion2_examen.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicionDao {
    // Obtiene todas las mediciones
    @Query("SELECT * FROM Medicion ORDER BY id")
    suspend fun getAll(): List<Medicion>

    // Obtiene una medición filtrando por su ID
    @Query("SELECT * FROM Medicion WHERE id = :id")
    suspend fun findById(id:Int):Medicion?

    // Inserta una medición
    @Insert
    suspend fun insertAll(vararg mediciones: Medicion)

    // (No utilizada) Modifica una medición
    @Update
    suspend fun update(medicion:Medicion)

    // (No utilizada) Elimina una medición
    @Delete
    suspend fun delete(medicion:Medicion)
}