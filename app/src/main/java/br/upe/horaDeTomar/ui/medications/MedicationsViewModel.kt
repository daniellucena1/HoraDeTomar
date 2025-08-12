package br.upe.horaDeTomar.ui.medications

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.data.manager.ScheduleAlarmManager
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmManager: ScheduleAlarmManager
): ViewModel(), AlarmActions{
    val medications: StateFlow<List<Medication>> = repository.medications
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    var medicationCreationState by mutableStateOf(
        Medication(
            name = "",
            via = "",
            dose = "",
            userId = 1
        )
    )
    private set

    fun updateMedicationCreationState(medication: Medication) {
        Log.d("MEDICATION UPDATE", "Updating medic ation: ${medication.name}")
        medicationCreationState = medication
    }

    suspend fun createMedication(
    ) {
        val newMedicationId = repository.insert(medicationCreationState)
        medicationCreationState = medicationCreationState.copy(id = newMedicationId.toInt())
        updateAlarmCreationState(alarmCreationState.copy(medicationId = newMedicationId.toInt()))
        Log.d("MEDICATION CREATION", "Medication created: ${medicationCreationState.name}")
        saveAlarm()
    }

    val alarmListState = alarmRepository.alarmList.asLiveData()

    var alarmCreationState by mutableStateOf(Alarm(medicationId = medicationCreationState.id))
        private set

    override fun updateAlarmCreationState(alarm: Alarm) {
        Log.d("ALARM UPDATE", "Updating alarm for medication: ${alarm.daysSelected} ${alarm.hour} : ${alarm.minute}")
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
            val nextILong = (lastId?:0L) + 1L
            val alarm = alarmRepository.getAlarmById(alarmCreationState.id)

            if (!alarmCreationState.isScheduled) {
                alarmCreationState.isScheduled = true
            }

            if (nextILong > Int.MAX_VALUE) {
                Log.e("MedicationsViewModel", "ID overflow, cannot create new alarm")
                throw IllegalStateException("ID overflow, cannot create new alarm")
            }

            listOf(
                async {
                    if (alarm?.id == alarmCreationState.id) {
                        updateAlarm(alarmCreationState)
                    } else {
                        alarmCreationState.id = nextILong.toInt()
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