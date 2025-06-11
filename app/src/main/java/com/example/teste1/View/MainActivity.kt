package com.example.teste1.View

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.teste1.AppDatabase
import com.example.teste1.DAO.MateriaDao
import com.example.teste1.MODEL.Materia
import com.example.teste1.R
import com.example.teste1.com.example.teste1.View.ThirdActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private val materias = mutableListOf<Materia>()
  private lateinit var adapter: MateriaAdapter
  private lateinit var dao: MateriaDao

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    dao = AppDatabase.getInstance(applicationContext).materiaDao()


    val listaMaterias = findViewById<ListView>(R.id.materias_list)
    val inputPesquisa = findViewById<EditText>(R.id.input_pesquisa_materia)
    val botaoAdicionar = findViewById<Button>(R.id.add_materia_button)

    adapter = MateriaAdapter(this, materias, dao)
    listaMaterias.adapter = adapter

    carregarMaterias()

    botaoAdicionar.setOnClickListener {
      adicionarMateria()
    }

    listaMaterias.setOnItemClickListener { _, _, position, _ ->
      val nomeMateria = materias[position].nomeMateria
      val intent = Intent(this, ThirdActivity::class.java)
      intent.putExtra("materiaNome", nomeMateria)
      startActivity(intent)
    }

    inputPesquisa.addTextChangedListener(object : TextWatcher {
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
      val nomeMateria = inputMateria.text.toString().trim()

      if (nomeMateria.isEmpty()) {
        Toast.makeText(this, "O nome da matéria não pode ser vazio!", Toast.LENGTH_SHORT).show()
        return@setPositiveButton
      }

      lifecycleScope.launch {
        val novaMateria = Materia(
          nomeMateria = nomeMateria,
          notas = "",
          pesoDosCriterios = "",
          soma = 0,
          converterPeso = 0
        )
        dao.inserir(novaMateria)
        materias.add(novaMateria)
        adapter.notifyDataSetChanged()
      }
    }
    dialog.setNegativeButton("Cancelar", null)
    dialog.show()
  }

  private fun carregarMaterias() {
    lifecycleScope.launch {
      val lista = dao.getAll()
      materias.clear()
      materias.addAll(lista)
      adapter.notifyDataSetChanged()
    }
  }
}
