package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Materias(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeMateria: String,
    val notas: String,
    val pesoDosCriterios: String,
    val soma: Int,
    val converterPeso: Int
)



