package br.upe.horaDeTomar.data.manager

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import br.upe.horaDeTomar.CHANNEL_ID
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import br.upe.horaDeTomar.ui.reminders.AlarmActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var medicationRepository: MedicationRepository

    @Inject
    lateinit var alarmRepository: AlarmRepository

    override fun onReceive(context: Context, intent: Intent?) {
        val alarmId = intent?.getIntExtra("ALARM_ID", -1) ?: return
        val medicationId = intent.getIntExtra("MEDICATION_ID", -1)

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val alarm = alarmRepository.getAlarmById(alarmId)
                val medication = medicationId.takeIf { it != -1 }?.let { medicationRepository.getById(it) }

                val medicationName = medication?.name ?: "Medicamento"
                val dose = medication?.dose ?: ""
                val time = alarm?.let { "${it.hour}:${it.minute}" } ?: ""

                val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
                    putExtra("alarmId", alarmId)
                    putExtra("MEDICATION_ID", medicationId)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    context, 
                    alarmId, 
                    alarmIntent, 
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                // Action for "Já Tomei"
                val takenIntent = Intent(context, NotificationActionReceiver::class.java).apply {
                    action = NotificationActionReceiver.ACTION_TAKEN
                    putExtra("ALARM_ID", alarmId)
                    putExtra("MEDICATION_ID", medicationId)
                }
                val takenPendingIntent = PendingIntent.getBroadcast(
                    context, 
                    alarmId * 2, 
                    takenIntent, 
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                // Action for "Adiar"
                val snoozeIntent = Intent(context, NotificationActionReceiver::class.java).apply {
                    action = NotificationActionReceiver.ACTION_SNOOZE
                    putExtra("ALARM_ID", alarmId)
                    putExtra("MEDICATION_ID", medicationId)
                }
                val snoozePendingIntent = PendingIntent.getBroadcast(
                    context, 
                    alarmId * 2 + 1, 
                    snoozeIntent, 
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.alarm_clock)
                    .setContentTitle("Hora de Tomar: $medicationName")
                    .setContentText("$dose · $time")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(VISIBILITY_PUBLIC)
                    .setFullScreenIntent(pendingIntent, true)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_pill, "Já Tomei", takenPendingIntent)
                    .addAction(R.drawable.alarm_clock, "Adiar", snoozePendingIntent)

                // Start service for sound if phone is not locked (activity not launched yet)
                context.startService(Intent(context, AlarmService::class.java))

                with(NotificationManagerCompat.from(context)) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        notify(alarmId, builder.build())
                    }
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
