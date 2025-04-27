package com.example.teste1

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private val avaliacoes = mutableListOf<Avaliacao>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var materiaNome: String
    private lateinit var mediaLabelFinal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        materiaNome = intent.getStringExtra("materiaNome") ?: "Matéria"

        val titleSecond = findViewById<TextView>(R.id.title_second)
        val listaAvaliacoes = findViewById<ListView>(R.id.lista_avaliacoes)
        val addAvaliacaoButton = findViewById<Button>(R.id.add_avaliacao_button)
        mediaLabelFinal = findViewById<TextView>(R.id.media_label_final)

        titleSecond.text = materiaNome

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listaAvaliacoes.adapter = adapter

        addAvaliacaoButton.setOnClickListener {
            adicionarAvaliacao()
        }
    }

    private fun adicionarAvaliacao() {
        val inputNota = EditText(this)
        inputNota.hint = "Digite a nota da avaliação"

        val inputPeso = EditText(this)
        inputPeso.hint = "Digite o peso (1 a 10)"
        inputPeso.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        val inputData = EditText(this)
        inputData.hint = "Digite a data (dd/MM/yyyy)"

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(inputNota)
            addView(inputPeso)
            addView(inputData)
        }

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Adicionar Avaliação")
        dialog.setView(layout)
        dialog.setPositiveButton("Adicionar") { _, _ ->
            val nota = inputNota.text.toString().toFloatOrNull() ?: 0f
            val peso = inputPeso.text.toString().toIntOrNull() ?: 1
            val data = inputData.text.toString().trim()

            if (nota in 0.0..10.0 && peso in 1..10 && data.isNotEmpty()) {
                val novaAvaliacao = Avaliacao(materiaNome, nota, peso, data)
                avaliacoes.add(novaAvaliacao)
                atualizarLista()
                calcularMediaPonderada()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Cancelar", null)
        dialog.show()
    }

    private fun atualizarLista() {
        val listaAvaliacoesFormatada = avaliacoes.map {
            "${it.materia} | Nota: ${it.nota} | Peso: ${it.peso} | Data: ${it.data}"
        }
        adapter.clear()
        adapter.addAll(listaAvaliacoesFormatada)
        adapter.notifyDataSetChanged()
    }

    private fun calcularMediaPonderada() {
        if (avaliacoes.isNotEmpty()) {
            val somaPesos = avaliacoes.sumOf { it.peso }
            val somaNotasPonderadas = avaliacoes.sumOf { it.nota.toDouble() * it.peso.toDouble() }

            val media = if (somaPesos > 0) somaNotasPonderadas / somaPesos else 0.0
            mediaLabelFinal.text = "Média ponderada: %.2f".format(media)
        }
    }
}
