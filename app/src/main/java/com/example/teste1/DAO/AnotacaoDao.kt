package com.example.teste1.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.teste1.MODEL.Anotacao

@Dao
interface AnotacaoDao {
    @Insert
    suspend fun inserir(anotacao: Anotacao)

    @Update
    suspend fun atualizar(anotacao: Anotacao)

    @Delete
    suspend fun deletar(anotacao: Anotacao)

    @Query("SELECT * FROM Anotacao")
    suspend fun getAll(): List<Anotacao>
}
