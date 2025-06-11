package com.example.teste1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teste1.DAO.MateriaDao
import com.example.teste1.DAO.AnotacaoDao
import com.example.teste1.DAO.AvaliacaoDao
import com.example.teste1.MODEL.Materia
import com.example.teste1.MODEL.Anotacao
import com.example.teste1.MODEL.Avaliacao

@Database(entities = [Materia::class, Anotacao::class, Avaliacao::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun materiaDao(): MateriaDao
    abstract fun anotacaoDao(): AnotacaoDao
    abstract fun avaliacaoDao(): AvaliacaoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
