package com.example.teste1.com.example.teste1.MODEL

import android.os.Parcel
import android.os.Parcelable

data class Avaliacao(
    val materia: String,
    var nota: Float,
    var peso: Float,
    var tipo: String,
    var dataAvaliacao: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(materia)
        parcel.writeFloat(nota)
        parcel.writeFloat(peso)
        parcel.writeString(tipo)
        parcel.writeString(dataAvaliacao)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Avaliacao> {
        override fun createFromParcel(parcel: Parcel): Avaliacao = Avaliacao(parcel)
        override fun newArray(size: Int): Array<Avaliacao?> = arrayOfNulls(size)
    }
}
