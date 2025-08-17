package br.upe.horaDeTomar.data.receiver

import androidx.work.Data
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.worker.ALARM_TAG
import br.upe.horaDeTomar.data.worker.AlarmWorker
import br.upe.horaDeTomar.data.worker.RESCHEDULE_ALARM_TAG
import br.upe.horaDeTomar.data.worker.RescheduleAlarmWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.HashMap
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadCastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var scheduleAlarmManager: ScheduleAlarmManager

    @Inject
    lateinit var workRequestManager: WorkRequestManager

    private val broadcastReceiverScope = CoroutineScope(SupervisorJob())

    override fun onReceive(p0: Context?, p1: Intent?) {
        val pendingResult: PendingResult = goAsync()

        broadcastReceiverScope.launch(Dispatchers.Default) {
            try {
                p1?.let { intent ->
                    when (intent.action) {
                        "android.intent.action.BOOT_COMPLETED" -> {
                            workRequestManager.enqueueWorker<RescheduleAlarmWorker>(
                                RESCHEDULE_ALARM_TAG,
                            )
                        }

                        ACTION_DISMISS -> workRequestManager.cancelWork("alarmTag")
                        ACTION_SNOOZE -> {
                            scheduleAlarmManager.snooze(1)
                            workRequestManager.cancelWork("alarmTag")
                        }
                        else -> {
                            val shouldStartWorker = alarmIsToday(intent)
                            val inputData = Data.Builder()
                                .putString(HOUR, intent.getStringExtra(HOUR))
                                .putString(MEDICATION_ID, intent.getStringExtra(MEDICATION_ID))
                                .putString(MINUTE, intent.getStringExtra(MINUTE))
                                .build()
                            if (shouldStartWorker) {
                                workRequestManager.enqueueWorker<AlarmWorker>(
                                    ALARM_TAG,
                                    inputData,
                                )
                            }
                        }
                    }
                }
            } finally {
                pendingResult.finish()
                broadcastReceiverScope.cancel()
            }
        }
    }
}

private fun alarmIsToday(intent: Intent): Boolean {
    val daysSelected = intent.extras?.getSerializable(DAYS_SELECTED) as? HashMap<String, Boolean>
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return daysSelected?.get(getDayOfWeek(today)) ?: false
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