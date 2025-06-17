package com.example.teste1.DAO

import androidx.room.*
import com.example.teste1.MODEL.Anotacao

@Dao
interface AnotacaoDao {

    @Insert
    suspend fun insert(anotacao: Anotacao)

    @Update
    suspend fun update(anotacao: Anotacao)

    @Delete
    suspend fun delete(anotacao: Anotacao)

    @Query("SELECT * FROM anotacoes WHERE id = :id")
    suspend fun getById(id: Long): Anotacao?

    @Query("SELECT * FROM anotacoes WHERE nomeAvaliacao = :nomeAvaliacao")
    suspend fun getByNomeAvaliacao(nomeAvaliacao: String): List<Anotacao>
}