package com.example.teste1

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teste1.DAO.MateriasDao
import com.example.teste1.DAO.AvaliacaoDao
import com.example.teste1.MODEL.Materias
import com.example.teste1.MODEL.Avaliacao

@Database(entities = [Materias::class, Avaliacao::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun materiasDao(): MateriasDao
    abstract fun avaliacaoDao(): AvaliacaoDao
}
