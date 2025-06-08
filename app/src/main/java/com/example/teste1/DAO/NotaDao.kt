package com.example.teste1.DAO

import androidx.room.*
import com.example.teste1.MODEL.Nota

@Dao
interface NotaDao {

    @Insert
    suspend fun inserir(nota: Nota)

    @Update
    suspend fun atualizar(nota: Nota)

    @Delete
    suspend fun deletar(nota: Nota)

    @Query("SELECT * FROM Nota")
    suspend fun listarTodas(): List<Nota>

    @Query("SELECT * FROM Nota WHERE id = :id")
    suspend fun buscarPorId(id: Int): Nota?
}
