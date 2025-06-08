package com.example.teste1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import com.example.teste1.MODEL.Nota

class NotasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotaAdapter
    private val notasList = mutableListOf<Nota>()

    private val REQUEST_CODE_CRIAR_NOTA = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas_activity)

        recyclerView = findViewById(R.id.recycler_notas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotaAdapter(this, notasList,
            onEditClick = { nota, pos ->
                val intent = Intent(this, NotaDetalheActivity::class.java)
                intent.putExtra(NotaDetalheActivity.EXTRA_NOTA, nota)
                intent.putExtra(NotaDetalheActivity.EXTRA_POSICAO, pos)
                startActivityForResult(intent, REQUEST_CODE_CRIAR_NOTA)
            },
            onDeleteClick = { _, pos ->
                notasList.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                Toast.makeText(this, "Nota apagada", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter

        // 🔹 Agora o botão abre a tela de adicionar nota
        val botaoAdicionarNota = findViewById<Button>(R.id.add_nota_button)
        botaoAdicionarNota.setOnClickListener {
            val intent = Intent(this, NotaDetalheActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CRIAR_NOTA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CRIAR_NOTA && resultCode == Activity.RESULT_OK) {
            val novaNota = data?.getSerializableExtra(NotaDetalheActivity.EXTRA_NOTA) as? Nota
            val apagar = data?.getBooleanExtra("apagar", false) ?: false
            val posicao = data?.getIntExtra(NotaDetalheActivity.EXTRA_POSICAO, -1) ?: -1

            if (novaNota != null && !apagar) {
                if (posicao != -1) {
                    notasList[posicao] = novaNota
                    adapter.notifyItemChanged(posicao)
                } else {
                    notasList.add(novaNota)
                    adapter.notifyDataSetChanged()
                }
            } else if (apagar && posicao != -1) {
                notasList.removeAt(posicao)
                adapter.notifyItemRemoved(posicao)
            }
        }
    }
}
