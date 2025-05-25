package com.example.teste1.DAO

import androidx.room.*
import com.example.teste1.MODEL.Avaliacao

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
}
