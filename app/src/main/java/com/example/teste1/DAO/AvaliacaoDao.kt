package com.example.teste1.DAO

<<<<<<< Updated upstream
import androidx.room.*
import com.example.teste1.MODEL.Avaliacao

=======
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.teste1.MODEL.Avaliacao

import java.util.List;

>>>>>>> Stashed changes
@Dao
interface AvaliacaoDao {

    @Insert
    suspend fun inserir(avaliacao: Avaliacao)

    @Update
    suspend fun atualizar(avaliacao: Avaliacao)

    @Delete
    suspend fun deletar(avaliacao: Avaliacao)

    @Query("SELECT * FROM Avaliacao")
    suspend fun listarTodas(): List<Avaliacao>

    @Query("SELECT * FROM Avaliacao WHERE id = :id")
    suspend fun buscarPorId(id: Int): Avaliacao?
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
