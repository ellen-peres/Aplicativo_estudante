package com.example.teste1.DAO

import androidx.room.*
import com.example.teste1.MODEL.Avaliacao

@Dao
interface AvaliacaoDao {
    @Insert
    suspend fun inserir(avaliacao: Avaliacao)

    @Query("SELECT * FROM Avaliacao WHERE materia = :materia")
    suspend fun getByMateria(materia: String): List<Avaliacao>
}

