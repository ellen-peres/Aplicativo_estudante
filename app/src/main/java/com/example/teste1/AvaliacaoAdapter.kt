package com.example.teste1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.MODEL.Avaliacao

class AvaliacaoAdapter(private val context: Context, private val listaAvaliacoes: List<Avaliacao>) :
    RecyclerView.Adapter<AvaliacaoAdapter.AvaliacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvaliacaoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_avaliacao, parent, false)
        return AvaliacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvaliacaoViewHolder, position: Int) {
        val avaliacao = listaAvaliacoes[position]
        holder.bind(avaliacao)
    }

    override fun getItemCount(): Int = listaAvaliacoes.size

    class AvaliacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeAvaliacao = itemView.findViewById<TextView>(R.id.nome_avaliacao)
        private val notaAvaliacao = itemView.findViewById<TextView>(R.id.nota_avaliacao)

        fun bind(avaliacao: Avaliacao) {
            nomeAvaliacao.text = avaliacao.materia
            notaAvaliacao.text = "Nota: ${avaliacao.nota}"
        }
    }
}
