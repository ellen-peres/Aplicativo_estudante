package com.example.teste1

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditPesoActivity : AppCompatActivity() {

    private lateinit var avaliacoes: MutableList<Avaliacao>
    private lateinit var adapter: AvaliacaoPesoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_peso)

        avaliacoes = intent.getParcelableArrayListExtra("avaliacoes") ?: mutableListOf()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_edit_peso)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AvaliacaoPesoAdapter(avaliacoes)
        recyclerView.adapter = adapter

        val botaoSalvar = findViewById<Button>(R.id.botao_salvar)
        botaoSalvar.setOnClickListener {
            // Retorna a lista atualizada para ThirdActivity
            val intent = intent
            intent.putParcelableArrayListExtra("avaliacoes_editadas", ArrayList(avaliacoes))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
