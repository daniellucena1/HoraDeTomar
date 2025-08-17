package br.upe.horaDeTomar.data.worker

import android.Manifest
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import br.upe.horaDeTomar.data.manager.WorkRequestManager
import br.upe.horaDeTomar.data.receiver.MEDICATION_ID
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.util.helper.ALARM_WORKER_NOTIFICATION_ID
import br.upe.horaDeTomar.util.helper.AlarmNotificationHelper
import br.upe.horaDeTomar.util.helper.MediaPlayerHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collectLatest

@HiltWorker
class AlarmWorker @AssistedInject constructor (
    private val alarmRepository: AlarmRepository,
    private val alarmNotificationHelper: AlarmNotificationHelper,
    private val mediaPlayerHelper: MediaPlayerHelper,
    private val workRequestManager: WorkRequestManager,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(ctx, params){
    @RequiresPermission(Manifest.permission.VIBRATE)
    override suspend fun doWork(): Result {
        return try {
            mediaPlayerHelper.prepare()
            val title = inputData.getString(MEDICATION_ID) ?: ""
            val time = "${inputData.getString("HOUR")}:${inputData.getString("MINUTE")}"

            val foregroundInfo = ForegroundInfo(
                ALARM_WORKER_NOTIFICATION_ID,
                alarmNotificationHelper.getAlarmBaseNotification(title, time).build(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK else 0
            )
            setForeground(foregroundInfo)

            mediaPlayerHelper.start()

            alarmRepository.getAlarmByTime(
                hour = time.substringBefore(':'),
                minute = time.substringAfter(':'),
            ).collectLatest {
                it?.let {
                    it.isScheduled = false
                    alarmRepository.update(it)
                }
            }

            Result.success()
        } catch (e: CancellationException) {
            alarmNotificationHelper.removeAlarmWorkerNotification()
            mediaPlayerHelper.release()
            workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)
            Result.failure()
        } catch (t: Throwable) {
            alarmNotificationHelper.removeAlarmWorkerNotification()
            mediaPlayerHelper.release()
            workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)
            Log.d("[ALARM WORKER]", "doWork FAILURE", t)
            Result.failure()
        }
    }
}

const val ALARM_TAG = "alarmTag"