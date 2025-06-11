package com.example.teste1.com.example.teste1.View

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.R
import com.example.teste1.AppDatabase
import com.example.teste1.MODEL.Avaliacao
import kotlinx.coroutines.launch
import java.util.*

class ThirdActivity : AppCompatActivity() {

    private val REQUEST_CODE_EDIT_PESO = 1
    private val avaliacoes = mutableListOf<Avaliacao>()
    private lateinit var adapter: AvaliacaoAdapter
    private lateinit var materiaNome: String
    private lateinit var mediaLabelTop: TextView
    private lateinit var mediaLabelBottom: TextView
    private lateinit var botaoEditarPeso: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        materiaNome = intent.getStringExtra("materiaNome") ?: "Matéria não encontrada"
        Toast.makeText(this, "Matéria recebida: $materiaNome", Toast.LENGTH_SHORT).show()

        val tituloMateria = findViewById<EditText>(R.id.input_materia)
        tituloMateria.setText(materiaNome)

        mediaLabelTop = findViewById(R.id.media_label_top)
        mediaLabelBottom = findViewById(R.id.media_label_bottom)

        val listaAvaliacoes = findViewById<RecyclerView>(R.id.recycler_avaliacoes)
        listaAvaliacoes.layoutManager = LinearLayoutManager(this)

        adapter = AvaliacaoAdapter(this, avaliacoes) { calcularMedia() }
        listaAvaliacoes.adapter = adapter

        botaoEditarPeso = findViewById(R.id.edit_peso_button)
        botaoEditarPeso.setOnClickListener {
            abrirActivityEditPeso()
        }

        val botaoAdicionar = findViewById<Button>(R.id.add_avaliacao_button)
        botaoAdicionar.setOnClickListener {
            adicionarAvaliacao()
        }

        carregarAvaliacoes() // <-- carrega do banco ao iniciar
    }

    private fun abrirActivityEditPeso() {
        val intent = Intent(this, EditPesoActivity::class.java)
        intent.putParcelableArrayListExtra("avaliacoes", ArrayList(avaliacoes))
        startActivityForResult(intent, REQUEST_CODE_EDIT_PESO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_EDIT_PESO && resultCode == RESULT_OK) {
            val avaliacoesEditadas = data?.getParcelableArrayListExtra<Avaliacao>("avaliacoes_editadas")
            if (avaliacoesEditadas != null) {
                avaliacoes.clear()
                avaliacoes.addAll(avaliacoesEditadas)
                adapter.notifyDataSetChanged()
                calcularMedia()
            }
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
        inputNota.hint = "Digite a nota (opcional)"
        layout.addView(inputNota)

        val tipoSpinner = Spinner(this)
        val tipos = arrayOf("Prova", "Trabalho", "Atividade")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipos)
        tipoSpinner.adapter = adapterSpinner
        layout.addView(tipoSpinner)

        AlertDialog.Builder(this)
            .setTitle("Adicionar Avaliação")
            .setView(layout)
            .setPositiveButton("Adicionar") { _, _ ->
                val nomeAvaliacao = inputNome.text.toString().trim()
                val notaTexto = inputNota.text.toString().trim()
                val nota = notaTexto.toFloatOrNull() ?: -1f
                val tipoSelecionado = tipoSpinner.selectedItem.toString()

                if (nomeAvaliacao.isEmpty()) {
                    Toast.makeText(this, "Digite um nome válido!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val novaAvaliacao = Avaliacao(
                    materia = materiaNome,
                    nomeAvaliacao = nomeAvaliacao,
                    nota = nota,
                    peso = 1.0f,
                    tipo = tipoSelecionado,
                    dataAvaliacao = ""
                )

                val calendario = Calendar.getInstance()
                val year = calendario.get(Calendar.YEAR)
                val month = calendario.get(Calendar.MONTH)
                val day = calendario.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(this, { _, ano, mes, dia ->
                    val dataSelecionada = "$dia/${mes + 1}/$ano"
                    novaAvaliacao.dataAvaliacao = dataSelecionada

                    lifecycleScope.launch {
                        AppDatabase.getInstance(applicationContext).avaliacaoDao().inserir(novaAvaliacao)
                        avaliacoes.add(novaAvaliacao)
                        adapter.notifyDataSetChanged()
                        calcularMedia()
                    }

                }, year, month, day).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun carregarAvaliacoes() {
        lifecycleScope.launch {
            val lista = AppDatabase.getInstance(applicationContext)
                .avaliacaoDao()
                .getByMateria(materiaNome)

            avaliacoes.clear()
            avaliacoes.addAll(lista)
            adapter.notifyDataSetChanged()
            calcularMedia()
        }
    }

    private fun calcularMedia() {
        if (avaliacoes.isEmpty()) {
            mediaLabelBottom.text = "0/10"
            return
        }

        var somaPesos = 0f
        var somaNotas = 0f

        for (avaliacao in avaliacoes) {
            if (avaliacao.nota >= 0f) {
                somaNotas += avaliacao.nota * avaliacao.peso
                somaPesos += avaliacao.peso
            }
        }

        val media: Float = if (somaPesos > 0) (somaNotas / somaPesos) else 0f
        mediaLabelBottom.text = String.format("%.1f/10", media)
    }
}
