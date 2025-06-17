package com.example.teste1.View

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.teste1.AppDatabase
import com.example.teste1.MODEL.Anotacao
import com.example.teste1.R
import kotlinx.coroutines.launch

class NotaDetalheActivity : AppCompatActivity() {

    private lateinit var editTitulo: EditText
    private lateinit var editTexto: EditText
    private lateinit var botaoSalvar: Button
    private lateinit var botaoApagar: Button

    private var anotacaoId: Long? = null
    private var nomeAvaliacao: String? = null

    private val anotacaoDao by lazy {
        AppDatabase.getInstance(applicationContext).anotacaoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nota_detalhe_activity)

        editTitulo = findViewById(R.id.edit_titulo)
        editTexto = findViewById(R.id.edit_texto)
        botaoSalvar = findViewById(R.id.btn_salvar)
        botaoApagar = findViewById(R.id.btn_apagar)

        anotacaoId = intent.getLongExtra("anotacao_id", -1L).takeIf { it != -1L }
        nomeAvaliacao = intent.getStringExtra("nome_avaliacao")

        if (anotacaoId != null) {
            lifecycleScope.launch {
                val anotacao = anotacaoDao.getById(anotacaoId!!)
                anotacao?.let {
                    editTitulo.setText(it.titulo)
                    editTexto.setText(it.texto)
                }
            }
        }

        botaoSalvar.setOnClickListener { salvarOuAtualizar() }
        botaoApagar.setOnClickListener { deletarAnotacao() }
    }

    private fun salvarOuAtualizar() {
        val titulo = editTitulo.text.toString().trim()
        val texto = editTexto.text.toString().trim()

        if (titulo.isEmpty() || texto.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val nomeAval = nomeAvaliacao
        if (nomeAval.isNullOrEmpty()) {
            Toast.makeText(this, "Nome da avaliação não fornecido", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            if (anotacaoId != null) {
                val atualizada = Anotacao(id = anotacaoId!!, titulo = titulo, texto = texto, nomeAvaliacao = nomeAval)
                anotacaoDao.update(atualizada)
                Toast.makeText(this@NotaDetalheActivity, "Anotação atualizada", Toast.LENGTH_SHORT).show()
            } else {
                val nova = Anotacao(titulo = titulo, texto = texto, nomeAvaliacao = nomeAval)
                anotacaoDao.insert(nova)
                Toast.makeText(this@NotaDetalheActivity, "Anotação salva", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun deletarAnotacao() {
        anotacaoId?.let { id ->
            lifecycleScope.launch {
                val anotacao = anotacaoDao.getById(id)
                anotacao?.let {
                    anotacaoDao.delete(it)
                    Toast.makeText(this@NotaDetalheActivity, "Anotação apagada", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}