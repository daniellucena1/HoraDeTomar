package br.upe.horaDeTomar.ui.medications

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.Alarm
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.data.manager.AlarmScheduler
import br.upe.horaDeTomar.data.repositories.AlarmRepository
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel(), AlarmActions {

    val medications: StateFlow<List<Medication>> = repository.medications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val alarmListState: StateFlow<List<Alarm>> = alarmRepository.alarmList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var medicationCreationState by mutableStateOf(
        Medication(name = "", via = "", dose = "", userId = 1, imageUri = "")
    )
        private set

    var pendingAlarms = mutableStateListOf<Alarm>()
        private set

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun preparePendingAlarms(rep: Int, medicationId: Int? = null) {
        val mId = medicationId ?: medicationCreationState.id
        while (pendingAlarms.size < rep) pendingAlarms.add(Alarm(medicationId = mId))
        while (pendingAlarms.size > rep) pendingAlarms.removeLast()
        if (medicationId != null) {
            for (i in 0 until pendingAlarms.size) {
                pendingAlarms[i] = pendingAlarms[i].copy(medicationId = medicationId)
            }
        }
    }

    private fun String.two(): String = this.padStart(2, '0')
    fun updatePendingAlarm(index: Int, hour: String? = null, minute: String? = null, daysJson: String? = null) {
        val curr = pendingAlarms.getOrNull(index) ?: return
        pendingAlarms[index] = curr.copy(
            hour = (hour ?: curr.hour).two(),
            minute = (minute ?: curr.minute).two(),
            daysSelectedJson = daysJson ?: curr.daysSelectedJson
        )
    }

    fun clearPendingAlarms() {
        pendingAlarms.clear()
    }

    fun updateMedicationCreationState(medication: Medication) {
        Log.d("MEDICATION UPDATE", "Updating medication: ${medication.name}")
        medicationCreationState = medication
    }

    fun loadMedicationForEditing(medicationId: Int) {
        viewModelScope.launch {
            repository.getById(medicationId)?.let { med ->
                medicationCreationState = med
                val alarms = alarmRepository.getAlarmsForMedicationOnce(medicationId)
                pendingAlarms.clear()
                pendingAlarms.addAll(alarms)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun updateMedication() {
        repository.update(medicationCreationState)
        val existingAlarms = alarmRepository.getAlarmsForMedicationOnce(medicationCreationState.id)
        existingAlarms.forEach { alarmScheduler.cancelAlarm(it) }
        existingAlarms.forEach { alarmRepository.delete(it) }
        saveAllPendingAlarmsForMedication(medicationCreationState.id)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun createMedication() {
        val newMedicationId = repository.insert(medicationCreationState).toInt()
        medicationCreationState = medicationCreationState.copy(id = newMedicationId)
        if (pendingAlarms.isEmpty()) {
            pendingAlarms.add(Alarm(medicationId = newMedicationId, isScheduled = true))
        } else {
            preparePendingAlarms(pendingAlarms.size, medicationId = newMedicationId)
        }
        saveAllPendingAlarmsForMedication(newMedicationId)
    }

    private suspend fun saveAllPendingAlarmsForMedication(medicationId: Int) {
        for (a in pendingAlarms) {
            val toInsert = a.copy(medicationId = medicationId, isScheduled = true)
            val id = alarmRepository.insert(toInsert).toInt()
            val saved = toInsert.copy(id = id)
            alarmScheduler.schedule(saved)
        }
        clearPendingAlarms()
    }

    var alarmCreationState by mutableStateOf(Alarm(medicationId = medicationCreationState.id))
        private set

    override fun updateAlarmCreationState(alarm: Alarm) {
        Log.d("ALARM UPDATE", "Updating alarm: ${alarm.daysSelected} ${alarm.hour}:${alarm.minute}")
        alarm.setDaysSelected(alarm.daysSelected)
        alarmCreationState = alarm
    }

    override fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarm.setDaysSelected(alarm.daysSelected)
            alarmRepository.update(alarm)
            if (alarm.isScheduled) alarmScheduler.schedule(alarm) else alarmScheduler.cancelAlarm(alarm)
        }
    }

    override fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.delete(alarm)
            if (alarm.isScheduled) alarmScheduler.cancelAlarm(alarm)
        }
    }

    fun deleteMedication(medication: Medication) {
        viewModelScope.launch {
            val alarms = alarmRepository.getAlarmsForMedicationOnce(medication.id)
            alarms.forEach { alarm ->
                if (alarm.isScheduled) {
                    alarmScheduler.cancelAlarm(alarm)
                }
                alarmRepository.delete(alarm)
            }
            repository.delete(medication)
        }
    }

    override fun saveAlarm() {
        viewModelScope.launch {
            val exists = alarmRepository.getAlarmById(alarmCreationState.id)
            if (exists != null) {
                val upd = alarmCreationState.copy(isScheduled = true)
                alarmRepository.update(upd)
                alarmScheduler.schedule(upd)
            } else {
                val id = alarmRepository.insert(alarmCreationState.copy(isScheduled = true)).toInt()
                val saved = alarmCreationState.copy(id = id)
                alarmCreationState = saved
                alarmScheduler.schedule(saved)
            }
        }
    }

    suspend fun getMedicationByAlarmId(alarmId: Int): Medication? {
        val alarm = alarmRepository.getAlarmById(alarmId) ?: return null
        return repository.getById(alarm.medicationId)
    }

}
