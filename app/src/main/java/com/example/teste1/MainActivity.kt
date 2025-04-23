package com.example.teste1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
  private val materias = mutableListOf<String>() // Lista de matérias adicionadas

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)

    // Aplicando as margens das barras do sistema
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    // Referências aos elementos do layout
    val inputMateria = findViewById<EditText>(R.id.input_materia)
    val addCriterioButton = findViewById<Button>(R.id.add_criterio_button)
    val materiasList = findViewById<ListView>(R.id.materias_list)
    val goToSecondActivityButton = findViewById<Button>(R.id.go_to_second_activity)

    // Adaptador para exibir as matérias na ListView
    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, materias)
    materiasList.adapter = adapter

    // Configuração do botão para adicionar matérias
    addCriterioButton.setOnClickListener {
      val materia = inputMateria.text.toString()
      if (materia.isNotEmpty()) {
        materias.add(materia) // Adiciona a matéria à lista
        adapter.notifyDataSetChanged() // Atualiza a lista exibida
        inputMateria.text.clear() // Limpa o campo de entrada
      }
    }

    // Configuração do botão para ir para a SecondActivity
    goToSecondActivityButton.setOnClickListener {
      val intent = Intent(this, SecondActivity::class.java)
      startActivity(intent)
    }
  }
}

class SecondActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)
  }
}
