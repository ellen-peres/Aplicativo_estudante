package com.example.teste1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teste1.DAO.MateriaDao
import com.example.teste1.DAO.AnotacaoDao
import com.example.teste1.MODEL.Materia
import com.example.teste1.MODEL.Anotacao

@Database(entities = [Materia::class, Anotacao::class], version = 2) // Inclui Anotacao
abstract class AppDatabase : RoomDatabase() {

    abstract fun materiaDao(): MateriaDao
    abstract fun anotacaoDao(): AnotacaoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
