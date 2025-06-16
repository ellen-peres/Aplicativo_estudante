package com.example.teste1.com.example.teste1.View

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.MODEL.Avaliacao
import com.example.teste1.databinding.EditPesoItemBinding

class AvaliacaoPesoAdapter(
    private val avaliacoes: MutableList<Avaliacao>
) : RecyclerView.Adapter<AvaliacaoPesoAdapter.ViewHolder>() {

    class ViewHolder(val binding: EditPesoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EditPesoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = avaliacoes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avaliacao = avaliacoes[position]

        holder.binding.nomeAvaliacao.text = avaliacao.nomeAvaliacao
        holder.binding.notaAvaliacao.text = String.format("%.1f/10", avaliacao.nota)
        holder.binding.inputPeso.setText(avaliacao.peso.toString())

        holder.binding.inputPeso.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val peso = s.toString().toFloatOrNull() ?: 0f
                avaliacao.peso = peso
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
