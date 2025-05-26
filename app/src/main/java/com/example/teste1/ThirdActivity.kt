package com.example.teste1

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ThirdActivity : AppCompatActivity() {
    private val avaliacoes = mutableListOf<Avaliacao>()
    private lateinit var adapter: AvaliacaoAdapter
    private lateinit var materiaNome: String
    private lateinit var mediaLabelTop: TextView
    private lateinit var mediaLabelBottom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        materiaNome = intent.getStringExtra("materiaNome") ?: "Matéria"

        val tituloMateria = findViewById<EditText>(R.id.input_materia)
        tituloMateria.setText(materiaNome)

        mediaLabelTop = findViewById(R.id.media_label_top)
        mediaLabelBottom = findViewById(R.id.media_label_bottom)

        val listaAvaliacoes = findViewById<RecyclerView>(R.id.recycler_avaliacoes)
        listaAvaliacoes.layoutManager = LinearLayoutManager(this)

        adapter = AvaliacaoAdapter(this, avaliacoes) { calcularMedia() }
        listaAvaliacoes.adapter = adapter

        val botaoAdicionar = findViewById<Button>(R.id.add_avaliacao_button)
        botaoAdicionar.setOnClickListener {
            adicionarAvaliacao()
        }
    }

    private fun adicionarAvaliacao() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 16, 32, 16)

        val inputNome = EditText(this)
        inputNome.hint = "Digite o nome da avaliação"
        layout.addView(inputNome)

        val inputNota = EditText(this)
        inputNota.hint = "Digite a nota"
        layout.addView(inputNota)

        val tipoSpinner = Spinner(this)
        val tipos = arrayOf("Prova", "Trabalho")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipos)
        tipoSpinner.adapter = adapterSpinner
        layout.addView(tipoSpinner)

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Adicionar Avaliação")
        dialog.setView(layout)
        dialog.setPositiveButton("Adicionar") { _, _ ->
            val nomeAvaliacao = inputNome.text.toString().trim()
            val nota = inputNota.text.toString().toFloatOrNull()
            val tipoSelecionado = tipoSpinner.selectedItem.toString()

            if (nomeAvaliacao.isEmpty() || nota == null) {
                Toast.makeText(this, "Digite um nome e uma nota válidos!", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val novaAvaliacao = Avaliacao(nomeAvaliacao, nota, 1.0f, tipoSelecionado)
            avaliacoes.add(novaAvaliacao)
            adapter.notifyDataSetChanged()
            calcularMedia()
        }
        dialog.setNegativeButton("Cancelar", null)
        dialog.show()
    }

    private fun calcularMedia() {
        if (avaliacoes.isEmpty()) {
            mediaLabelBottom.text = "0/10"
            return
        }

        var somaPesos = 0f
        var somaNotas = 0f

        for (avaliacao in avaliacoes) {
            somaNotas += avaliacao.nota * avaliacao.peso
            somaPesos += avaliacao.peso
        }

        val media: Float = if (somaPesos > 0) (somaNotas / somaPesos) else 0f
        mediaLabelBottom.text = String.format("%.1f/10", media)
    }
}
