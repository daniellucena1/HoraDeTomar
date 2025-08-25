package br.upe.horaDeTomar.data.worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.util.helper.AlarmNotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

@HiltWorker
class AlarmCheckerWorker @AssistedInject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmNotificationHelper: AlarmNotificationHelper,
    private val workRequestManager: WorkRequestManager,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(ctx, params){
    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        return try {
            val scheduledAlarms = alarmRepository.alarmList
                .map { alarmList ->
                    alarmList.filter { alarm -> alarm.isScheduled }
                }.firstOrNull()

            if (scheduledAlarms?.isNotEmpty() == true && !alarmNotificationHelper.alarmCheckerNotificationPresent()) {
                alarmNotificationHelper.displayAlarmCheckerNotification()
            }

            if (scheduledAlarms?.isEmpty() == true ) {
                alarmNotificationHelper.removeScheduledAlarmNotification()
            }

            Result.success()
        } catch (thowable: Throwable) {
            Log.d("[ALARM_CHECKER_WORKER]", "doWork completed, FAILURE", thowable)
            Result.failure()
        }
    }
}

const val ALARM_CHECKER_TAG = "ALARM_CHECKER_TAG"