package com.example.teste1.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.teste1.MODEL.Anotacao


@Dao
interface AnotacaoDao {
    @Insert fun insert(anotacao: Anotacao): Long
    @Query("SELECT * FROM Anotacao") fun getAll(): List<Anotacao>
}