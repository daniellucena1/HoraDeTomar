package br.upe.horaDeTomar.ui.medications

import br.upe.horaDeTomar.data.entities.Alarm

interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm) {}
    fun updateAlarm(alarm: Alarm) {}
    fun removeAlarm(alarm: Alarm) {}
    fun saveAlarm() {}
    fun clearAlarm() {}
}