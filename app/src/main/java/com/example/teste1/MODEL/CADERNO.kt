package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CADERNO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val capitulo: String
)