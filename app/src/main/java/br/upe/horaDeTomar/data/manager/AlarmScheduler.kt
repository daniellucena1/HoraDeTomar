package br.upe.horaDeTomar.data.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import br.upe.horaDeTomar.data.entities.Alarm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager
) {

    private val gson = Gson()

    fun schedule (alarm: Alarm) {
        val hour = alarm.hour.toIntOrNull() ?: 0
        val minute = alarm.minute.toIntOrNull() ?: 0
        val daysSelected = alarm.daysSelected

        daysSelected.forEach { (day, selected) ->
            if (selected) {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)

                    val dayOfWeek = when (day) {
                        "Dom" -> Calendar.SUNDAY
                        "Seg" -> Calendar.MONDAY
                        "Ter" -> Calendar.TUESDAY
                        "Qua" -> Calendar.WEDNESDAY
                        "Qui" -> Calendar.THURSDAY
                        "Sex" -> Calendar.FRIDAY
                        "Sab" -> Calendar.SATURDAY
                        else -> null
                    }

                    dayOfWeek?.let {
                        set(Calendar.DAY_OF_WEEK, it)

                        if(before(Calendar.getInstance())) {
                            add(Calendar.WEEK_OF_YEAR, 1)
                        }
                    }
                }

                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("ALARM_ID", alarm.id)
                    putExtra("MEDICATION_ID", alarm.medicationId)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    alarm.id + day.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    fun snooze(alarmId: Int, medicationId: Int, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            putExtra("MEDICATION_ID", medicationId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }


    fun cancelAlarm(alarm: Alarm) {
        val daysSelected = alarm.daysSelected

        daysSelected.forEach { (day, selected) ->
            if (selected) {
                val intent = Intent(context, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    alarm.id + day.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
        }
        Log.d("ALARM_SCHEDULE", "Alarme cancelado: ${alarm.id}")
    }
}