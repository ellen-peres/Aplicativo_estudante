package com.example.teste1.View

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.teste1.DAO.MateriaDao
import com.example.teste1.MODEL.Materia
import com.example.teste1.R
import kotlinx.coroutines.launch

class MateriaAdapter(
    context: Context,
    private val materias: MutableList<Materia>,
    private val dao: MateriaDao
) : ArrayAdapter<Materia>(context, R.layout.item_materia, materias) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_materia, parent, false)
        val nomeMateria = view.findViewById<TextView>(R.id.nome_materia)
        val botaoExcluir = view.findViewById<Button>(R.id.delete_materia_button)

        val materia = materias[position]
        nomeMateria.text = materia.nomeMateria

        botaoExcluir.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Excluir Matéria")
                .setMessage("Tem certeza que deseja excluir '${materia.nomeMateria}'?")
                .setPositiveButton("Sim") { _, _ ->
                    (context as AppCompatActivity).lifecycleScope.launch {
                        dao.deletar(materia)
                        materias.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                .setNegativeButton("Não", null)
                .show()
        }

        return view
    }
}
