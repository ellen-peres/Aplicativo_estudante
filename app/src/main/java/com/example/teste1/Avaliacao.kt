package com.example.teste1.MODEL

<<<<<<< Updated upstream
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
=======
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Avaliacao")
>>>>>>> Stashed changes
data class Avaliacao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val materia: String,
<<<<<<< Updated upstream
    val nota: Float,
    val peso: Int,
    val data: String
)
=======
    var nota: Float,
    var peso: Float,
    var tipo: String,
    var dataAvaliacao: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        materia = parcel.readString() ?: "",
        nota = parcel.readFloat(),
        peso = parcel.readFloat(),
        tipo = parcel.readString() ?: "",
        dataAvaliacao = parcel.readString() ?: "",
        id = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(materia)
        parcel.writeFloat(nota)
        parcel.writeFloat(peso)
        parcel.writeString(tipo)
        parcel.writeString(dataAvaliacao)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Avaliacao> {
        override fun createFromParcel(parcel: Parcel): Avaliacao = Avaliacao(parcel)
        override fun newArray(size: Int): Array<Avaliacao?> = arrayOfNulls(size)
    }
}
>>>>>>> Stashed changes
