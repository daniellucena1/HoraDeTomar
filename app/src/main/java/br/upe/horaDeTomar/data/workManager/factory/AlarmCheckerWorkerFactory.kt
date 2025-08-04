package br.upe.horaDeTomar.data.workManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.workManager.worker.AlarmCheckerWorker
import br.upe.horaDeTomar.util.helper.AlarmNotificationHelper
import javax.inject.Inject

class AlarmCheckerWorkerFactory @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmNotificationHelper: AlarmNotificationHelper,
    private val workRequestManager: WorkRequestManager
) : ChildWorkerFactory {
    override fun create(appContext: Context, params: WorkerParameters) : ListenableWorker {
        return AlarmCheckerWorker(alarmRepository, alarmNotificationHelper, workRequestManager, appContext, params)
    }
}