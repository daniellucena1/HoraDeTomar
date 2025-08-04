package br.upe.horaDeTomar.ui.reminders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmManager: ScheduleAlarmManager,
) : ViewModel(), AlarmActions {

    val alarmListState = alarmRepository.alarmList.asLiveData()

    val alarmCreationState by mutableStateOf(Alarm())
        private set
}