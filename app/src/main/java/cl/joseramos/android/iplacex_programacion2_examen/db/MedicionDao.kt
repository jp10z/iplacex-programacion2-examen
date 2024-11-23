package cl.joseramos.android.iplacex_programacion2_examen.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicionDao {
    @Query("SELECT * FROM Medicion ORDER BY id")
    suspend fun getAll(): List<Medicion>

    @Query("SELECT * FROM Medicion WHERE id = :id")
    suspend fun findById(id:Int):Medicion?

    @Insert
    suspend fun insertAll(vararg mediciones: Medicion)

    @Update
    suspend fun update(medicion:Medicion)

    @Delete
    suspend fun delete(medicion:Medicion)
}