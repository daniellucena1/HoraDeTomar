package br.upe.horaDeTomar.data.workManager.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

@HiltWorker
class RescheduleAlarmWorker @AssistedInject constructor(
    @Assisted private val alarmRepository: AlarmRepository,
    @Assisted private val scheduleAlarmManager: ScheduleAlarmManager,
    @Assisted private val workRequestManager: WorkRequestManager,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(ctx, params){

    override suspend fun doWork() : Result {
        return try {
            val scheduledAlarms = alarmRepository.alarmList
                .map { alarmsList ->
                    alarmsList.filter { alarm ->
                        alarm.isScheduled
                    }
                }
                .firstOrNull() { it.isNotEmpty() }
            scheduledAlarms?.forEach {
                scheduleAlarmManager.schedule(it)
            }
            workRequestManager.cancelWork(RESCHEDULE_ALARM_TAG)

            Result.success()
        } catch (throwable: Throwable){
            Result.failure()
        }
    }
}

const val RESCHEDULE_ALARM_TAG = "rescheduleAlarmTag"