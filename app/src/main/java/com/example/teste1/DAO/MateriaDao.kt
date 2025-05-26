package com.example.teste1.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.teste1.MODEL.Avaliacao
import com.example.teste1.MODEL.Materias

@Dao
interface MateriasDao {

    @Insert
    suspend fun inserir(materias: Materias)

    @Update
    suspend fun atualizar(materias: Materias)

    @Delete
    suspend fun deletar(materias: Materias)

    @Query("SELECT * FROM Materias")
    suspend fun getAll(): List<Materias>
}
