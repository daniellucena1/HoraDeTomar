package br.upe.horaDeTomar.data.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.receiver.AlarmBroadCastReceiver
import br.upe.horaDeTomar.data.receiver.DAYS_SELECTED
import br.upe.horaDeTomar.data.receiver.HOUR
import br.upe.horaDeTomar.data.receiver.IS_RECURRING
import br.upe.horaDeTomar.data.receiver.MINUTE
import br.upe.horaDeTomar.data.worker.ALARM_CHECKER_TAG
import br.upe.horaDeTomar.data.worker.AlarmCheckerWorker
import br.upe.horaDeTomar.util.GlobalProperties.pendingIntentFlagg
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import java.util.Calendar
import java.util.Random
import javax.inject.Inject

class ScheduleAlarmManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val workRequestManager: WorkRequestManager,
) {
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.M)
    fun schedule(alarm: Alarm) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(applicationContext, AlarmBroadCastReceiver::class.java).apply {
            action = "br.upe.horaDeTomar.ACTION_ALARM_TRIGGERED"
            putExtra("ALARM_ID", alarm.id)
            putExtra(IS_RECURRING, true)
            putExtra(MINUTE, alarm.minute)
            putExtra(HOUR, alarm.hour)
            putExtra(DAYS_SELECTED, HashMap(alarm.daysSelected))
        }

        val alarmPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            alarm.id,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarm.hour.toInt())
            set(Calendar.MINUTE, alarm.minute.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmPendingIntent
        )

        Toast.makeText(
            applicationContext,
            "Alarme agendado para ${alarm.hour}:${alarm.minute}",
            Toast.LENGTH_SHORT
        ).show()

        // worker opcional
        workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)
    }

    fun cancel(alarm: Alarm) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alamIntent = Intent(applicationContext, AlarmBroadCastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            alarm.id,
            alamIntent,
            pendingIntentFlagg,
        )
        val toastText = "Alarm canceled for ${alarm.hour}:${alarm.minute}"

        workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)

        handler.post {
            Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
        }

        alarmManager.cancel(alarmPendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun snooze(medicationId: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 10)

        val alarm = Alarm(
            id = Random().nextInt(Integer.MAX_VALUE),
            hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)),
            minute = String.format("%02d", calendar.get(Calendar.MINUTE)),
            isScheduled = true,
            medicationId = medicationId
        )

        schedule(alarm)
    }

    suspend fun clearScheduledAlarms(alarmsList: List<Alarm>) {
        alarmsList.asFlow().buffer().collect {
            if (it.isScheduled) {
                cancel(it)
            }
        }
    }
}