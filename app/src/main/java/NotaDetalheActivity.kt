package com.example.teste1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.teste1.MODEL.Nota
import java.io.Serializable

class NotaDetalheActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTA = "extra_nota"
        const val EXTRA_POSICAO = "extra_posicao"
        const val REQUEST_CODE_SELECIONAR_IMAGEM = 200
    }

    private lateinit var editTitulo: EditText
    private lateinit var editTexto: EditText
    private lateinit var imagemNota: ImageView
    private lateinit var botaoAdicionarImagem: Button
    private var posicao: Int = -1
    private var imagemUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nota_detalhe_activity)

        editTitulo = findViewById(R.id.edit_titulo)
        editTexto = findViewById(R.id.edit_texto)
        imagemNota = findViewById(R.id.imagem_nota)
        botaoAdicionarImagem = findViewById(R.id.btn_add_imagem)

        val notaRecebida = intent.getSerializableExtra(EXTRA_NOTA) as? Nota
        posicao = intent.getIntExtra(EXTRA_POSICAO, -1)

        notaRecebida?.let {
            editTitulo.setText(it.titulo)
            editTexto.setText(it.texto)
            if (imagemUri != null) {
                imagemNota.setImageURI(imagemUri)
            }
        }

        botaoAdicionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_SELECIONAR_IMAGEM)
        }

        val botaoSalvar = findViewById<Button>(R.id.btn_salvar)
        botaoSalvar.setOnClickListener {
            salvarNota()
        }

        val botaoApagar = findViewById<Button>(R.id.btn_apagar)
        botaoApagar.setOnClickListener {
            apagarNota()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECIONAR_IMAGEM && resultCode == Activity.RESULT_OK) {
            imagemUri = data?.data
            imagemNota.setImageURI(imagemUri)
        }
    }

    private fun salvarNota() {
        val titulo = editTitulo.text.toString().trim()
        val texto = editTexto.text.toString().trim()

        if (titulo.isEmpty() || texto.isEmpty()) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            val novaNota = Nota(titulo, texto)
            val intent = Intent()
            intent.putExtra(EXTRA_NOTA, novaNota)
            intent.putExtra(EXTRA_POSICAO, posicao)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    private fun apagarNota() {
        val intent = Intent()
        intent.putExtra(EXTRA_POSICAO, posicao)
        intent.putExtra("apagar", true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
