package com.example.teste1

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teste1.DAO.MateriaDao
import com.example.teste1.MODEL.Materia


@Database(entities = [Materia::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun materiaDao(): MateriaDao
}
