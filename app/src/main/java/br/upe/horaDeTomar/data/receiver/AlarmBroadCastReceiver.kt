package br.upe.horaDeTomar.data.receiver

import android.app.PendingIntent
import androidx.work.Data
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import br.upe.horaDeTomar.data.AppDatabase
import br.upe.horaDeTomar.data.daos.AlarmDao
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.worker.ALARM_CHECKER_TAG
import br.upe.horaDeTomar.data.worker.ALARM_TAG
import br.upe.horaDeTomar.data.worker.AlarmCheckerWorker
import br.upe.horaDeTomar.data.worker.AlarmWorker
import br.upe.horaDeTomar.data.worker.RESCHEDULE_ALARM_TAG
import br.upe.horaDeTomar.data.worker.RescheduleAlarmWorker
import br.upe.horaDeTomar.util.helper.AlarmNotificationHelper
import br.upe.horaDeTomar.util.helper.MediaPlayerHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.HashMap
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: AlarmNotificationHelper

    @Inject
    lateinit var mediaPlayerHelper: MediaPlayerHelper

    @Inject
    lateinit var alarmDao: AlarmDao

    @Inject
    lateinit var scheduleAlarmManager: ScheduleAlarmManager

    @Inject
    lateinit var workRequestManager: WorkRequestManager

    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            when (intent.action) {
                ACTION_ALARM_TRIGGERED -> {
                    val alarmId = intent.getIntExtra("ALARM_ID", -1)
                    val alarm = if (alarmId != -1) alarmDao.getAlarmById(alarmId) else null

                    alarm?.let {
                        // Atualiza status do alarme
                        alarmDao.update(it.copy(isScheduled = true))
                        scheduleAlarmManager.schedule(it)

                        val notification = notificationHelper.getAlarmBaseNotification(it.medicationId.toString(), "${it.hour}:${it.minute}").build()
                        notificationHelper.showAlarmNotification(notification)
                        // Toca som e vibração
                        mediaPlayerHelper.prepare()
                        mediaPlayerHelper.start()
                    }
                }
                ACTION_DISMISS -> {
                    notificationHelper.removeAlarmWorkerNotification()

                    val alarmId = intent.getIntExtra("ALARM_ID", -1)
                    if (alarmId != -1) {
                        val alarm = alarmDao.getAlarmById(alarmId)
                        if (alarm != null) {
                            alarmDao.update(alarm.copy(isScheduled = false))
                            scheduleAlarmManager.cancel(alarm)
                            workRequestManager.cancelWork(ALARM_CHECKER_TAG)
                        }
                    }
                }

                ACTION_SNOOZE -> {
                    notificationHelper.removeAlarmWorkerNotification()

                    val alarmId = intent.getIntExtra("ALARM_ID", -1)
                    if (alarmId != -1) {
                        val alarm = alarmDao.getAlarmById(alarmId)
                        if (alarm != null) {
                            val calendar = Calendar.getInstance().apply {
                                timeInMillis = System.currentTimeMillis()
                                add(Calendar.MINUTE, 5)
                            }

                            val snoozedAlarm = alarm.copy(
                                hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)),
                                minute = String.format("%02d", calendar.get(Calendar.MINUTE)),
                                isScheduled = true
                            )

                            alarmDao.update(snoozedAlarm)

                            scheduleAlarmManager.schedule(snoozedAlarm)

                            workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)
                        }
                    }
                }
            }
            pendingResult.finish()
        }
    }

    companion object {
        const val ACTION_DISMISS = "br.upe.horaDeTomar.ACTIONS_DISMISS"
        const val ACTION_SNOOZE = "br.upe.horaDeTomar.ACTION_SNOOZE"

        const val ACTION_ALARM_TRIGGERED = "br.upe.horaDeTomar.ACTION_ALARM_TRIGGERED"

        fun Class<*>.setIntentAction(
            actionName: String,
            requestCode: Int,
            context: Context
        ): PendingIntent {
            val intent = Intent(context, this).apply { action = actionName }
            return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}


private fun getDayOfWeek(day: Int): String {
    return when (day) {
        Calendar.SUNDAY -> "Dom"
        Calendar.MONDAY -> "Seg"
        Calendar.TUESDAY -> "Ter"
        Calendar.WEDNESDAY -> "Qua"
        Calendar.THURSDAY -> "Qui"
        Calendar.FRIDAY -> "Sex"
        Calendar.SATURDAY -> "Sab"
        else -> ""
    }
}

const val IS_RECURRING = "IS_RECURRING"
const val DAYS_SELECTED = "DAYS_SELECTED"
const val MEDICATION_ID = "MEDICATION_ID"
const val HOUR = "HOUR"
const val MINUTE = "MINUTE"
const val ACTION_DISMISS = "ACTION_DISMISS"
const val ACTION_SNOOZE = "ACTION_SNOOZE"