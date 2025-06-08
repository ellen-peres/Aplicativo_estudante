package com.example.teste1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.MODEL.Nota

class NotaAdapter(
    private val context: Context,
    private val notas: MutableList<Nota>,
    private val onEditClick: (nota: Nota, position: Int) -> Unit,
    private val onDeleteClick: (nota: Nota, position: Int) -> Unit
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    inner class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloNota: TextView = itemView.findViewById(R.id.titulo_nota)
        val textoNotaPreview: TextView = itemView.findViewById(R.id.texto_nota_preview)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit_nota)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete_nota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.tituloNota.text = nota.titulo
        holder.textoNotaPreview.text = if (nota.texto.length > 100) {
            nota.texto.substring(0, 100) + "..."
        } else {
            nota.texto
        }

        holder.buttonEdit.setOnClickListener {
            onEditClick(nota, position)
        }
        holder.buttonDelete.setOnClickListener {
            onDeleteClick(nota, position)
        }
    }

    override fun getItemCount(): Int = notas.size
}
