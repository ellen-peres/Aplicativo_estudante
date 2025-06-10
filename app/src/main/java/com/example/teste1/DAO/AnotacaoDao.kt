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
    suspend fun insert(anotacao: Anotacao): Long

    @Query("SELECT * FROM Anotacao")
    suspend fun getAll(): List<Anotacao>

    @Query("SELECT * FROM Anotacao WHERE id = :id")
    suspend fun getById(id: Long): Anotacao?

    @Update
    suspend fun update(anotacao: Anotacao)

    @Delete
    suspend fun delete(anotacao: Anotacao)
}
