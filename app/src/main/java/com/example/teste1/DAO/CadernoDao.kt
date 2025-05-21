package com.example.teste1.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.teste1.MODEL.Caderno

@Dao
interface CadernoDao {
    @Insert fun insert(caderno: Caderno): Long
    @Query("SELECT * FROM Caderno") fun getAll(): List<Caderno>
}