package com.example.teste1.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.teste1.MODEL.Avaliacao
import com.example.teste1.MODEL.Materia

@Dao
interface MateriaDao {
    @Insert
    suspend fun inserir(materias: Materia)

    @Update
    suspend fun atualizar(materias: Materia)

    @Delete
    suspend fun deletar(materias: Materia)

    @Query("SELECT * FROM Materia")
    suspend fun getAll(): List<Materia>
}
