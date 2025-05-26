package com.example.teste1

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotasActivity : AppCompatActivity() {
    private lateinit var materiaNome: String
    private val notas = mutableListOf<Nota>()
    private lateinit var adapter: NotaAdapter
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_activity)

        materiaNome = intent.getStringExtra("materiaNome") ?: "Matéria"
        val tituloMateria = findViewById<TextView>(R.id.nome_avaliacao)
        tituloMateria.text = materiaNome

        val listaNotas = findViewById<RecyclerView>(R.id.recycler_notas)
        listaNotas.layoutManager = LinearLayoutManager(this)

        adapter = NotaAdapter(this, notas) { nota ->
            abrirDialogEdicao(nota)
        }
        listaNotas.adapter = adapter

        searchBar = findViewById(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarNotas(s.toString())
            }
        })

        // ✅ Correção do evento de clique do botão de adicionar anotação
        val botaoAdicionar = findViewById<Button>(R.id.add_nota_button)
        botaoAdicionar.setOnClickListener {
            adicionarNota()
        }
    }

    private fun adicionarNota() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 16, 32, 16)

        val inputTitulo = EditText(this)
        inputTitulo.hint = "Digite o título da anotação"
        layout.addView(inputTitulo)

        val inputTexto = EditText(this)
        inputTexto.hint = "Digite sua anotação"
        layout.addView(inputTexto)

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Adicionar Nota")
        dialog.setView(layout)
        dialog.setPositiveButton("Salvar") { _, _ ->
            val tituloNota = inputTitulo.text.toString().trim()
            val textoNota = inputTexto.text.toString().trim()
            val novaNota = Nota(tituloNota, textoNota, null)
            notas.add(novaNota)
            adapter.notifyItemInserted(notas.size - 1)
        }
        dialog.setNegativeButton("Cancelar", null)
        dialog.show()
    }

    private fun abrirDialogEdicao(nota: Nota) {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 16, 32, 16)

        val inputTitulo = EditText(this)
        inputTitulo.setText(nota.titulo)
        layout.addView(inputTitulo)

        val inputTexto = EditText(this)
        inputTexto.setText(nota.texto)
        layout.addView(inputTexto)

        val imagemNota = ImageView(this)
        imagemNota.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200)
        imagemNota.setPadding(0, 16, 0, 16)
        if (nota.imagem == null) {
            imagemNota.visibility = View.GONE
        } else {
            imagemNota.setImageBitmap(nota.imagem)
        }
        layout.addView(imagemNota)

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Editar Nota")
        dialog.setView(layout)
        dialog.setPositiveButton("Salvar") { _, _ ->
            nota.titulo = inputTitulo.text.toString().trim()
            nota.texto = inputTexto.text.toString().trim()
            adapter.notifyDataSetChanged()
        }
        dialog.setNegativeButton("Cancelar", null)
        dialog.show()
    }

    private fun filtrarNotas(textoPesquisa: String) {
        val notasFiltradas = notas.filter { it.titulo.contains(textoPesquisa, ignoreCase = true) || it.texto.contains(textoPesquisa, ignoreCase = true) }
        adapter.atualizarLista(notasFiltradas)
    }
}
