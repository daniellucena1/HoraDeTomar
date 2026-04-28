package br.upe.horaDeTomar.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action ?: return
        val alarmId = intent.getIntExtra("ALARM_ID", -1)
        val medicationId = intent.getIntExtra("MEDICATION_ID", -1)

        // Stop the alarm sound/service
        context.stopService(Intent(context, AlarmService::class.java))

        // Dismiss the notification
        if (alarmId != -1) {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.cancel(alarmId)
        }

        when (action) {
            ACTION_TAKEN -> {
                // No additional logic for now, medication taken
            }
            ACTION_SNOOZE -> {
                if (alarmId != -1 && medicationId != -1) {
                    val cal = Calendar.getInstance().apply {
                        add(Calendar.MINUTE, 1)
                    }
                    alarmScheduler.snooze(
                        alarmId,
                        medicationId,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE)
                    )
                }
            }
        }
    }

    companion object {
        const val ACTION_TAKEN = "br.upe.horaDeTomar.ACTION_TAKEN"
        const val ACTION_SNOOZE = "br.upe.horaDeTomar.ACTION_SNOOZE"
    }
}
