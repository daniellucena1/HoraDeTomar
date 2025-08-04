package br.upe.horaDeTomar.util.helper

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import br.upe.horaDeTomar.MainActivity
import br.upe.horaDeTomar.data.receiver.ACTION_DISMISS
import br.upe.horaDeTomar.data.receiver.ACTION_SNOOZE
import br.upe.horaDeTomar.data.receiver.AlarmBroadCastReceiver
import br.upe.horaDeTomar.util.GlobalProperties.pendingIntentFlagg
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.content.ContextCompat
import br.upe.horaDeTomar.R
import br.upe.horaDeTomar.util.setIntentAction
@Singleton
class AlarmNotificationHelper @Inject constructor(
    @ApplicationContext private val applicationContext: Context
){
    private val notificationManager = NotificationManagerCompat.from(applicationContext)

    private val alarmBroadCastReceiver = AlarmBroadCastReceiver::class.java

    private val openAlarmListIntent = Intent(
        Intent.ACTION_VIEW,
        "https://www.clock.com/AlarmsList".toUri(),
        applicationContext,
        MainActivity::class.java,
    ).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    private val openAlarmPendingIntent = PendingIntent.getActivity(
        applicationContext,
        13,
        openAlarmListIntent,
        pendingIntentFlagg,
    )

    private val dismissIntentAction = alarmBroadCastReceiver.setIntentAction(
        actionName = ACTION_DISMISS,
        requestCode = 14,
        context = applicationContext
    )

    private val snoozeIntentAction = alarmBroadCastReceiver.setIntentAction(
        actionName = ACTION_SNOOZE,
        requestCode = 15,
        context = applicationContext
    )

    init {
        createAlarmNotificationChannels()
    }

    fun getAlarmBaseNotification(title: String, time: String) =
        NotificationCompat.Builder(applicationContext, ALARM_WORKER_CHANNEL_ID)
            .setContentTitle(time)
            .setContentText(title)
            .setSmallIcon(R.drawable.alarm_clock)
            .setShowWhen(false)
            .setFullScreenIntent(openAlarmPendingIntent, true)
            .setColor(ContextCompat.getColor(applicationContext, R.color.green_primary))
            .addAction(R.drawable.ic_close, "Dismiss", dismissIntentAction)
            .addAction(R.drawable.ic_alarm_clock_plus, "Snooze", snoozeIntentAction)
            .setOngoing(true)

    @SuppressLint("MissingPermission")
    fun displayAlarmCheckerNotification() {
        val alarmCheckerNotification = NotificationCompat.Builder(applicationContext, ALARM_CHECKER_CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm_clock)
            .setShowWhen(false)
            .setOngoing(true)
            .build()
        notificationManager.notify(ALARM_CHECKER_NOTIFICATION_ID, alarmCheckerNotification)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun alarmCheckerNotificationPresent(): Boolean {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val activeNotifications = notificationManager.activeNotifications

        for (notification in activeNotifications) {
            if (notification.id == ALARM_CHECKER_NOTIFICATION_ID) {
                return true
            }
        }

        return false
    }

    private fun createAlarmNotificationChannels() {
        val alarmWorkerChannel = NotificationChannelCompat.Builder(
            ALARM_WORKER_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_MAX,
        )
            .setName("Alarm Worker Channel")
            .setDescription("Shows an alert notification at scheduled time")
            .setSound(null, null)
            .build()

        val alarmCheckerChannel = NotificationChannelCompat.Builder(
            ALARM_CHECKER_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
        )
            .setName("Alarm Checker Channel")
            .setDescription("Shows a notification when any alarm is scheduled")
            .setSound(null, null)
            .build()

        notificationManager.createNotificationChannelsCompat(
            listOf(
                alarmWorkerChannel,
                alarmCheckerChannel,
            ),
        )
    }

    fun removeAlarmWorkerNotification() {
        notificationManager.cancel(ALARM_WORKER_NOTIFICATION_ID)
    }

    fun removeScheduledAlarmNotification() {
        notificationManager.cancel(ALARM_CHECKER_NOTIFICATION_ID)
    }
}

private const val ALARM_WORKER_CHANNEL_ID = "alarm_worker_channel"
const val ALARM_WORKER_NOTIFICATION_ID = 12
private const val ALARM_CHECKER_CHANNEL_ID = "alarm_checker_channel"
const val ALARM_CHECKER_NOTIFICATION_ID = 17