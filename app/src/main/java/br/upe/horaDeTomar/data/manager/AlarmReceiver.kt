package br.upe.horaDeTomar.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.upe.horaDeTomar.ui.reminders.AlarmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val alarmId = intent?.getIntExtra("ALARM_ID", -1) ?: return

        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            putExtra("alarmId", alarmId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        context.startActivity(alarmIntent)

    }
}