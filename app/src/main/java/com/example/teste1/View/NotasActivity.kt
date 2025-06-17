package com.example.teste1.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton // Importação do ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste1.AppDatabase
import com.example.teste1.MODEL.Anotacao
import com.example.teste1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.teste1.com.example.teste1.View.ThirdActivity



class NotasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotaAdapter
    private val anotacoesList = mutableListOf<Anotacao>()

    private lateinit var botaoAnterior: ImageButton // Variável do botão de voltar

    private val db by lazy { AppDatabase.getInstance(this) }
    private val anotacaoDao by lazy { db.anotacaoDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_activity)

        recyclerView = findViewById(R.id.recycler_notas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotaAdapter(
            this,
            anotacoesList,
            onEditClick = { anotacao, _ ->
                val intent = Intent(this, NotaDetalheActivity::class.java)
                intent.putExtra("anotacao_id", anotacao.id)
                startActivity(intent)
            },
            onDeleteClick = { anotacao, position ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        anotacaoDao.delete(anotacao)
                    }
                    anotacoesList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(this@NotasActivity, "Anotação apagada", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerView.adapter = adapter

        val botaoAdicionarNota = findViewById<Button>(R.id.add_nota_button)
        botaoAdicionarNota.setOnClickListener {
            val intent = Intent(this, NotaDetalheActivity::class.java)
            startActivity(intent)
        }

        botaoAnterior = findViewById(R.id.botao_anterior) // Configurando botão de voltar
        botaoAnterior.setOnClickListener {
            voltarParaThirdActivity()
        }
    }

    private fun voltarParaThirdActivity() { // Função de navegação para ThirdActivity
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
        finish() // Fecha NotasActivity para liberar memória e evitar sobrecarga de pilha
    }

    private fun carregarAnotacoes() {
        lifecycleScope.launch {
            val lista = withContext(Dispatchers.IO) {
                anotacaoDao.getAll()
            }
            anotacoesList.clear()
            anotacoesList.addAll(lista)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        carregarAnotacoes()
    }
}
