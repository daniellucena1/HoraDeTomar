package br.upe.horaDeTomar.ui.reminders

import br.upe.horaDeTomar.data.entities.Alarm


interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm) {}
    fun update(alarm: Alarm) {}
    fun remove(alarm: Alarm) {}
    fun save() {}
    fun clear() {}
}