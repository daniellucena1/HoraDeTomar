package br.upe.horaDeTomar.ui.medications

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.data.manager.AlarmScheduler
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
): ViewModel(), AlarmActions{
    val medications: StateFlow<List<Medication>> = repository.medications
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val alarmListState: StateFlow<List<Alarm>> = alarmRepository.alarmList
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



    var alarmCreationState by mutableStateOf(Alarm(medicationId = medicationCreationState.id))
        private set

    override fun updateAlarmCreationState(alarm: Alarm) {
        Log.d("ALARM UPDATE", "Updating alarm for medication: ${alarm.daysSelected} ${alarm.hour} : ${alarm.minute}")
        alarm.setDaysSelected(alarm.daysSelected)
        alarmCreationState = alarm
    }


    override fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarm.setDaysSelected(alarm.daysSelected)
            alarmRepository.update(alarm)

            if (alarm.isScheduled) {
                alarmScheduler.schedule(alarm)
            } else {
                alarmScheduler.cancelAlarm(alarm)
            }
        }
    }

    override fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.delete(alarm)

            if ( alarm.isScheduled ) {
                alarmScheduler.cancelAlarm(alarm)
            }
        }
    }

    override fun saveAlarm() {
        viewModelScope.launch {
            val alarm = alarmRepository.getAlarmById(alarmCreationState.id)

            if (alarm != null) {
                alarmRepository.update(alarmCreationState.copy(isScheduled = true))
            } else {
                val newId = alarmRepository.insert(alarmCreationState.copy(isScheduled = true))
                alarmCreationState = alarmCreationState.copy(id = newId.toInt())
            }

            alarmScheduler.schedule(alarmCreationState)
        }
    }

//    override fun clearAlarm() {
//        viewModelScope.launch {
//            alarmListState.value?.let {
//                listOf(
//                    async { alarmRepository.clear() },
//                    async { scheduleAlarmManager.clearScheduledAlarms(it) },
//                )
//            }
//        }
//    }
}