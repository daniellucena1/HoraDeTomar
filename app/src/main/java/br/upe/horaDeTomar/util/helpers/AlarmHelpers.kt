package br.upe.horaDeTomar.util.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import br.upe.horaDeTomar.data.entities.Alarm
import java.util.LinkedHashMap

private val gson = Gson()
private val DAYS_ORDER = listOf("Dom","Seg","Ter","Qua","Qui","Sex","Sab")

fun Map<String, Boolean>.toStableDaysJson(): String {
    val ordered = LinkedHashMap<String, Boolean>()
    DAYS_ORDER.forEach { d -> ordered[d] = (this[d] == true) }
    return gson.toJson(ordered)
}

fun String.toDaysMap(): Map<String, Boolean> {
    val type = object : TypeToken<Map<String, Boolean>>() {}.type
    val raw: Map<String, Boolean> = gson.fromJson(this, type) ?: emptyMap()
    val ordered = LinkedHashMap<String, Boolean>()
    DAYS_ORDER.forEach { d -> ordered[d] = (raw[d] == true) }
    return ordered
}

fun String.pad2(): String = padStart(2, '0')

fun formatTime(hour: String, minute: String): String = "${hour.pad2()}:${minute.pad2()}"

fun List<Alarm>.sortedTimesText(): String =
    this.sortedWith(compareBy({ it.hour.pad2() }, { it.minute.pad2() }))
        .joinToString(", ") { formatTime(it.hour, it.minute) }

fun Alarm.withDays(days: Map<String, Boolean>): Alarm =
    copy(daysSelectedJson = days.toStableDaysJson())
