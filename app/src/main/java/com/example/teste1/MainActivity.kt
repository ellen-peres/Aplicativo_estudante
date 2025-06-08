package com.example.teste1

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.teste1.MODEL.Materia
import com.example.teste1.DAO.MateriaDao
import com.seuapp.data.database.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private val materias = mutableListOf<String>()
  private lateinit var adapter: ArrayAdapter<String>
  private lateinit var dao: MateriaDao  // <- Agora é propriedade da classe

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val db = Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java,
      "materias.db"
    ).build()
    dao = db.materiaDao()

    // Inserir uma matéria exemplo só na primeira criação
    if (savedInstanceState == null) {
      lifecycleScope.launch {
        val novaMateria = Materia(
          nomeMateria = "ingles",
          notas = "teste 2",
          pesoDosCriterios = "Critério A: 40%, Critério B: 60%",
          soma = 100,
          converterPeso = 85
        )
        dao.inserir(novaMateria)
        carregarMaterias()
      }
    } else {
      carregarMaterias()
    }

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
        lifecycleScope.launch {
          val novaMateria = Materia(
            nomeMateria = materiaNome,
            notas = "",
            pesoDosCriterios = "",
            soma = 0,
            converterPeso = 0
          )
          dao.inserir(novaMateria)
          materias.add(materiaNome)
          adapter.notifyDataSetChanged()

          val listView = findViewById<ListView>(R.id.materias_list)
          listView.post {
            listView.smoothScrollToPosition(materias.size - 1)
          }
        }
      } else {
        Toast.makeText(this, "Nome da matéria não pode ser vazio!", Toast.LENGTH_SHORT).show()
      }
    }
    dialog.setNegativeButton("Cancelar", null)
    dialog.show()
  }

  private fun carregarMaterias() {
    lifecycleScope.launch {
      val lista = dao.getAll()
      materias.clear()
      materias.addAll(lista.map { it.nomeMateria })
      adapter.notifyDataSetChanged()
    }
  }
}
