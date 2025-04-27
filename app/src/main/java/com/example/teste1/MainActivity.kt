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

    val addMateriaButton = findViewById<Button>(R.id.add_materia_button)
    val materiasListView = findViewById<ListView>(R.id.materias_list)
    val inputPesquisaMateria = findViewById<EditText>(R.id.input_pesquisa_materia)

    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, materias)
    materiasListView.adapter = adapter

    addMateriaButton.setOnClickListener {
      adicionarMateria()
    }

    inputPesquisaMateria.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        adapter.filter.filter(s)
      }
    })

    materiasListView.setOnItemClickListener { _, _, position, _ ->
      val intent = Intent(this, SecondActivity::class.java)
      intent.putExtra("materiaNome", materias[position])
      startActivity(intent)
    }
  }

  private fun adicionarMateria() {
    val inputMateria = EditText(this)
    inputMateria.hint = "Digite o nome da matéria"

    val dialog = AlertDialog.Builder(this)
    dialog.setTitle("Adicionar Matéria")
    dialog.setView(inputMateria)
    dialog.setPositiveButton("Adicionar") { _, _ ->
      val materiaNome = inputMateria.text.toString().trim()
      if (materiaNome.isNotEmpty()) {
        materias.add(materiaNome)
        adapter.notifyDataSetChanged()

        val listView = findViewById<ListView>(R.id.materias_list)
        listView.post {
          listView.smoothScrollToPosition(materias.size - 1)
        }
      } else {
        Toast.makeText(this, "Nome da matéria não pode ser vazio!", Toast.LENGTH_SHORT).show()
      }
    }
    dialog.setNegativeButton("Cancelar", null)
    dialog.show()
  }
}
