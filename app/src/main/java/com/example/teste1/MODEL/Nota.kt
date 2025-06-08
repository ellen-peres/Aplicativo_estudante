package com.example.teste1.MODEL
import java.io.Serializable
import android.net.Uri



data class Nota(
    var titulo: String = "",
    var texto: String = "",
    var imagemUri: Uri? = null
): Serializable



