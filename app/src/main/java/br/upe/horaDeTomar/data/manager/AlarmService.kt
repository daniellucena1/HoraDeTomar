package br.upe.horaDeTomar.data.manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import br.upe.horaDeTomar.ui.reminders.AlarmActivity

class AlarmService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "alarm_channel")
            .setContentTitle("Hora de Tomar!")
            .setContentText("Está na hora de do seu medicamento")
            .setSmallIcon(R.drawable.alarm_clock)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(1, notification)

        mediaPlayer = MediaPlayer.create(this, R.raw.gran_vals)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}