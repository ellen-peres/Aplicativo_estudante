package com.example.teste1.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Nota")
data class Nota(
    var titulo: String = "",
    var texto: String = "",
    var imagemUri: String? = null, // armazenado como string

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Serializable
