package com.example.teste1.MODEL

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Avaliacao(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val materia: String,
    val nomeAvaliacao: String,
    var nota: Float,
    var peso: Float,
    var tipo: String,
    var dataAvaliacao: String = ""
)

 : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "", // novo campo: nomeAvaliacao
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(materia)
        parcel.writeString(nomeAvaliacao) // novo campo
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
