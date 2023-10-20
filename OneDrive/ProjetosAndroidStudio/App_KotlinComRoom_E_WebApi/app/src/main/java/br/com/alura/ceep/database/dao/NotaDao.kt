package br.com.alura.ceep.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.alura.ceep.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Insert(onConflict = REPLACE)
    suspend fun salva(note: Nota)

    @Insert(onConflict = REPLACE)
    fun salva(note: List<Nota>)

    @Query("SELECT * FROM Nota WHERE desativada = 0")
    fun buscaTodas(): Flow<List<Nota>>

    @Query("SELECT * FROM Nota WHERE id = :id AND desativada = 0")
    fun buscaPorId(id: String): Flow<Nota>

    @Query("DELETE FROM Nota WHERE id = :id")
    suspend fun remove(id: String)

    @Query("SELECT * FROM Nota WHERE sincronizada")
    fun buscaNaoSincronizada() : Flow<List<Nota>>

    @Query("UPDATE Nota SET desativada = 1 WHERE id = :id")
    fun desativa(id: String)

@Query("SELECT * FROM Nota WHERE id = 1")
    fun buscaDesativas() : Flow<List<Nota>>


}