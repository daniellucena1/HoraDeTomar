package br.upe.horaDeTomar.data.repositories

import br.upe.horaDeTomar.data.entities.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val alarmDao: br.upe.horaDeTomar.data.daos.AlarmDao
){
    val alarmList: Flow<List<Alarm>> = alarmDao.getAlarmsList()

    suspend fun insert(alarm: Alarm) : Long = alarmDao.insert(alarm)

    suspend fun update(alarm: Alarm) = alarmDao.update(alarm)

    suspend fun delete(alarm: Alarm) = alarmDao.delete(alarm)

    suspend fun getLastId() = alarmDao.getLastId()

    suspend fun clear() = alarmDao.clear()

    suspend fun getAlarmById(id: Int) = alarmDao.getAlarmById(id)

    fun getAlarmsForMedication(medicationId: Int): Flow<List<Alarm>> =
        alarmDao.getAlarmsForMedication(medicationId)

    suspend fun getAlarmsForMedicationOnce(medicationId: Int): List<Alarm> =
        alarmDao.getAlarmsForMedicationOnce(medicationId)

    fun getAlarmByTime(medicationId: Int, hour: String, minute: String): Flow<Alarm?> =
        alarmDao.getAlarmByTime(medicationId, hour, minute)
}