package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Avaliacao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val materia: String,
    val nota: Float,
    val peso: Int,
    val data: String
)
