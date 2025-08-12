package br.upe.horaDeTomar.ui.reminders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.ui.medications.AlarmActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmManager: ScheduleAlarmManager,
) : ViewModel(), AlarmActions {

    val alarmListState = alarmRepository.alarmList.asLiveData()

    var alarmCreationState by mutableStateOf(Alarm(medicationId = 0))
        private set

    override fun updateAlarmCreationState(alarm: Alarm) {
        alarm.setDaysSelected(alarm.daysSelected)
        alarmCreationState = alarm
    }

    override fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            listOf(
                async {
                    alarm.setDaysSelected(alarm.daysSelected)
                    alarmRepository.update(alarm)
                },
                async {
                    if (alarm.isScheduled) {
                        scheduleAlarmManager.schedule(alarm)
                    } else {
                        scheduleAlarmManager.cancel(alarm)
                    }
                },
            )
        }
    }

    override fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            listOf(
                async { alarmRepository.delete(alarm) },
                async {
                    if (alarm.isScheduled) {
                        scheduleAlarmManager.cancel(alarm)
                    }
                },
            )
        }
    }

    override fun saveAlarm() {
        viewModelScope.launch {
            val lastId = alarmRepository.getLastId()
            val alarm = alarmRepository.getAlarmById(alarmCreationState.id)

            if (!alarmCreationState.isScheduled) {
                alarmCreationState.isScheduled = true
            }

            listOf(
                async {
                    if (alarm?.id == alarmCreationState.id) {
                        updateAlarm(alarmCreationState)
                    } else {
                        alarmCreationState.id = (lastId?.plus(1) ?: 1) as Int
                        alarmCreationState.setDaysSelected(alarmCreationState.daysSelected)
                        alarmRepository.insert(alarmCreationState)
                    }
                },
                async { scheduleAlarmManager.schedule(alarmCreationState) },
            )
        }
    }

    override fun clearAlarm() {
        viewModelScope.launch {
            alarmListState.value?.let {
                listOf(
                    async { alarmRepository.clear() },
                    async { scheduleAlarmManager.clearScheduledAlarms(it) },
                )
            }
        }
    }

}