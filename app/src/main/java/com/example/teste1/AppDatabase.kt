package com.seuapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teste1.DAO.MateriaDao
import com.example.teste1.DAO.CadernoDao
import com.example.teste1.DAO.AnotacaoDao
import com.example.teste1.MODEL.Materia
import com.example.teste1.MODEL.Caderno
import com.example.teste1.MODEL.Anotacao

@Database(entities = [Materia::class, Caderno::class, Anotacao::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun materiaDao(): MateriaDao
    abstract fun cadernoDao(): CadernoDao
    abstract fun anotacaoDao(): AnotacaoDao
}
