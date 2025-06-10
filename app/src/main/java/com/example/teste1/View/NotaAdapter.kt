package com.example.teste1.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.MODEL.Anotacao
import com.example.teste1.R

class NotaAdapter(
    private val context: Context,
    private val anotacoes: MutableList<Anotacao>,
    private val onEditClick: (anotacao: Anotacao, position: Int) -> Unit,
    private val onDeleteClick: (anotacao: Anotacao, position: Int) -> Unit
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
        val anotacao = anotacoes[position]
        holder.tituloNota.text = anotacao.titulo
        holder.textoNotaPreview.text = if (anotacao.texto.length > 100) {
            anotacao.texto.substring(0, 100) + "..."
        } else {
            anotacao.texto
        }

        holder.buttonEdit.setOnClickListener {
            onEditClick(anotacao, position)
        }
        holder.buttonDelete.setOnClickListener {
            onDeleteClick(anotacao, position)
        }
    }

    override fun getItemCount(): Int = anotacoes.size
}
