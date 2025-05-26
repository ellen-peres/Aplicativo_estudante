package com.example.teste1

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotaAdapter(
    private val context: Context,
    private val listaNotas: MutableList<Nota>,
    private val onNotaClick: (Nota) -> Unit
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listaNotas[position]
        holder.bind(nota, onNotaClick)
    }

    override fun getItemCount(): Int = listaNotas.size

    fun atualizarLista(novasNotas: List<Nota>) {
        listaNotas.clear()
        listaNotas.addAll(novasNotas)
        notifyDataSetChanged()
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloNota: TextView = itemView.findViewById(R.id.titulo_nota)
        private val imagemNota: ImageView = itemView.findViewById(R.id.imagem_nota)

        fun bind(nota: Nota, onNotaClick: (Nota) -> Unit) {
            tituloNota.text = nota.titulo

            if (nota.imagem == null) {
                imagemNota.visibility = View.GONE // ✅ Oculta imagem se não houver
            } else {
                imagemNota.setImageBitmap(nota.imagem)
                imagemNota.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                onNotaClick(nota) // ✅ Abre edição ao clicar
            }
        }
    }
}
