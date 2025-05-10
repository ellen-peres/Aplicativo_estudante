package com.example.teste1.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.teste1.MODEL.Materia

@Dao
interface MateriaDao {
    @Insert fun insert(materia: Materia): Long
    @Query("SELECT * FROM Materia") fun getAll(): List<Materia>
}
