package com.example.teste1
import android.content.Intent

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class AvaliacaoAdapter(private val context: Context, private val listaAvaliacoes: List<Avaliacao>, private val atualizarMedia: () -> Unit) :
    RecyclerView.Adapter<AvaliacaoAdapter.AvaliacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvaliacaoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_avaliacao, parent, false)
        return AvaliacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvaliacaoViewHolder, position: Int) {
        val avaliacao = listaAvaliacoes[position]
        holder.bind(avaliacao, atualizarMedia, context)
    }

    override fun getItemCount(): Int = listaAvaliacoes.size

    class AvaliacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeAvaliacao = itemView.findViewById<TextView>(R.id.nome_avaliacao)
        private val notaAvaliacao = itemView.findViewById<TextView>(R.id.nota_avaliacao)
        private val botaoCalendario = itemView.findViewById<Button>(R.id.calendar_button)

        fun bind(avaliacao: Avaliacao, atualizarMedia: () -> Unit, context: Context) {
            nomeAvaliacao.text = avaliacao.materia
            notaAvaliacao.text = "${avaliacao.nota}/10"

            botaoCalendario.setOnClickListener {
                abrirCalendario(context, avaliacao)
            }

            // ✅ **Redireciona para `NotasActivity` ao clicar na avaliação**
            itemView.setOnClickListener {
                val intent = Intent(context, NotasActivity::class.java)
                intent.putExtra("materiaNome", avaliacao.materia) // Passa o nome da matéria
                context.startActivity(intent)
            }
        }

        private fun abrirCalendario(context: Context, avaliacao: Avaliacao) {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(context, { _, ano, mes, dia ->
                val dataSelecionada = "$dia/${mes + 1}/$ano"
                avaliacao.dataAvaliacao = dataSelecionada

                agendarNotificacao(context, dataSelecionada, avaliacao.materia)
            }, year, month, day).show()
        }

        private fun agendarNotificacao(context: Context, data: String, materia: String) {
            val channelId = "avaliacao_channel"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, "Lembrete de Avaliação", NotificationManager.IMPORTANCE_DEFAULT)
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }

            // ✅ **Verifica permissão antes de enviar a notificação**
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
                if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return // 🚨 **Se a permissão não estiver concedida, não envia**
                }
            }

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Avaliação marcada!")
                .setContentText("Sua avaliação de $materia está marcada para $data!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val manager = NotificationManagerCompat.from(context)
            manager.notify(1001, builder.build())
        }
    }
}
