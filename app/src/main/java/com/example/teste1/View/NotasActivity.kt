package com.example.teste1.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.AppDatabase
import com.example.teste1.MODEL.Anotacao
import com.example.teste1.R
import kotlinx.coroutines.launch

class NotasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotaAdapter
    private val anotacoesList = mutableListOf<Anotacao>()

    private lateinit var botaoAnterior: ImageButton

    private val db by lazy { AppDatabase.getInstance(this) }
    private val anotacaoDao by lazy { db.anotacaoDao() }

    private var nomeAvaliacao: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_activity)

        nomeAvaliacao = intent.getStringExtra("nome_avaliacao")

        recyclerView = findViewById(R.id.recycler_notas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotaAdapter(
            this,
            anotacoesList,
            onEditClick = { anotacao, _ ->
                val intent = Intent(this, NotaDetalheActivity::class.java)
                intent.putExtra("anotacao_id", anotacao.id)
                intent.putExtra("nome_avaliacao", nomeAvaliacao)
                startActivity(intent)
            },
            onDeleteClick = { anotacao, position ->
                lifecycleScope.launch {
                    anotacaoDao.delete(anotacao)
                    anotacoesList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(this@NotasActivity, "Anotação apagada", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerView.adapter = adapter

        findViewById<Button>(R.id.add_nota_button).setOnClickListener {
            val intent = Intent(this, NotaDetalheActivity::class.java)
            intent.putExtra("nome_avaliacao", nomeAvaliacao)
            startActivity(intent)
        }

        botaoAnterior = findViewById(R.id.botao_anterior)
        botaoAnterior.setOnClickListener { finish() }
    }

    private fun carregarAnotacoes() {
        nomeAvaliacao?.let { nome ->
            lifecycleScope.launch {
                val lista = anotacaoDao.getByNomeAvaliacao(nome)
                anotacoesList.clear()
                anotacoesList.addAll(lista)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        carregarAnotacoes()
    }
}