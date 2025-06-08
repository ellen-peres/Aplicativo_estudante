package com.example.teste1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private val materias = mutableListOf<String>()
  private lateinit var adapter: ArrayAdapter<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val listaMaterias = findViewById<ListView>(R.id.materias_list)
    val inputPesquisa = findViewById<EditText>(R.id.input_pesquisa_materia)
    val botaoAdicionar = findViewById<Button>(R.id.add_materia_button)

    adapter = ArrayAdapter(this, R.layout.item_materia, R.id.nome_materia, materias)
    listaMaterias.adapter = adapter

    // Adiciona espaçamento à lista
    listaMaterias.setPadding(12, 16, 12, 16)

    // Configura o botão de adicionar matéria
    botaoAdicionar.setOnClickListener {
      adicionarMateria()
    }

    // Configura o clique nos itens da lista
    listaMaterias.setOnItemClickListener { _, _, position, _ ->
      val intent = Intent(this, ThirdActivity::class.java)
      intent.putExtra("materiaNome", adapter.getItem(position))
      startActivity(intent)
    }

    // Implementa a funcionalidade de pesquisa
    inputPesquisa.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        // Não é necessário implementar
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Não é necessário implementar
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        adapter.filter.filter(s)
      }
    })
  }

  private fun adicionarMateria() {
    val layout = LinearLayout(this)
    layout.orientation = LinearLayout.VERTICAL
    layout.setPadding(32, 16, 32, 16)

    val inputNome = EditText(this)
    inputNome.hint = "Digite o nome da matéria"
    layout.addView(inputNome)

    val dialog = AlertDialog.Builder(this)
    dialog.setTitle("Adicionar Matéria")
    dialog.setView(layout)
    dialog.setPositiveButton("Adicionar") { _, _ ->
      val nomeMateria = inputNome.text.toString().trim()

      if (nomeMateria.isEmpty()) {
        Toast.makeText(this, "O nome da matéria não pode ser vazio!", Toast.LENGTH_SHORT).show()
        return@setPositiveButton
      }

      if (materias.contains(nomeMateria)) {
        Toast.makeText(this, "Matéria já existe!", Toast.LENGTH_SHORT).show()
        return@setPositiveButton
      }

      materias.add(nomeMateria)
      adapter.notifyDataSetChanged()
    }
    dialog.setNegativeButton("Cancelar", null)
    dialog.show()
  }
}
