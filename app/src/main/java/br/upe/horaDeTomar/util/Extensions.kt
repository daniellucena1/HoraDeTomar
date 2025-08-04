package br.upe.horaDeTomar.util

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.util.GlobalProperties.dateTimeFormatter
import java.time.LocalDateTime
import java.time.LocalTime

fun String?.parseInt() : Int {
    return if (this.isNullOrEmpty()) 0 else this.toInt()
}

fun String.checkNumberPicker(maxNumber: Int) : Boolean {
    return this.length <= 2 && this.isDigitsOnly() && this.parseInt() <= maxNumber
}

@RequiresApi(Build.VERSION_CODES.O)
fun Alarm.checkDate(): String {
    val currentTime = LocalDateTime.now()
    val alarmTime = LocalDateTime.of(currentTime.toLocalDate(), LocalTime.of(this.hour.parseInt(), this.minute.parseInt()))

    return when {
        currentTime.isBefore(alarmTime) -> {
            "Faltam ${alarmTime.hour - currentTime.hour} horas e ${alarmTime.minute - currentTime.minute} minutos"
        }
        else -> "Amanhã - ${alarmTime.plusDays(1).format(dateTimeFormatter)}"
    }


}

fun Class<out BroadcastReceiver>?.setIntentAction(
    actionName: String,
    requestCode: Int,
    context: Context
) : PendingIntent {
    val broadcastIntent = Intent(context, this).apply {
        action = actionName
    }
    return PendingIntent.getBroadcast(
        context,
        requestCode,
        broadcastIntent,
        GlobalProperties.pendingIntentFlagg
    )
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}
inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}