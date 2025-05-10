package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anotacao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tituloAnotacao: String,
    val texto: String
)