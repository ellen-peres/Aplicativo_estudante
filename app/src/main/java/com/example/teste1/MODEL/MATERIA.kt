package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MATERIA(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val notas: String,
    val pesoDosCritérios: String,
    val soma: Int,
    val converterPeso: Int
)

