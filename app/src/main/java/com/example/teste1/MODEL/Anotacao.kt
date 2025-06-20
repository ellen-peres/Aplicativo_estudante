package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anotacoes")
data class Anotacao(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String,
    val texto: String,
    val nomeAvaliacao: String
)