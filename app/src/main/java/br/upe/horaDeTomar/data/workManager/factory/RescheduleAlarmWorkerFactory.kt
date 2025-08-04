package br.upe.horaDeTomar.data.workManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.workManager.worker.RescheduleAlarmWorker
import javax.inject.Inject

class RescheduleAlarmWorkerFactory @Inject constructor(
    private val rescheduleAlarmManager: ScheduleAlarmManager,
    private val alarmRepository: AlarmRepository,
    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {
    override fun create (appContext: Context, params: WorkerParameters) : ListenableWorker {
        return RescheduleAlarmWorker(
            alarmRepository,
            rescheduleAlarmManager,
            workRequestManager,
            appContext,
            params
        )
    }
}