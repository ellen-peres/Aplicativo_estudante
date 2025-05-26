package com.example.teste1

data class Avaliacao(
    val materia: String,
    val nota: Float,
    var peso: Float,
    val tipo: String,
    var dataAvaliacao: String = "" // Adicionando o atributo de data
)