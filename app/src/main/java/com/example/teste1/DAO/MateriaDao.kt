package com.example.teste1.DAO

import androidx.room.*
import com.example.teste1.MODEL.Materia

@Dao
interface MateriaDao {
    @Insert
    suspend fun inserir(materia: Materia)

    @Query("SELECT * FROM Materia")
    suspend fun getAll(): List<Materia>

    @Update
    suspend fun atualizar(materia: Materia)

    @Delete
    suspend fun deletar(materia: Materia)
}
