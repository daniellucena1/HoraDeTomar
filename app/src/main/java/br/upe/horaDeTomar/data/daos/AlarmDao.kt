package br.upe.horaDeTomar.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.upe.horaDeTomar.data.entities.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(alarm: Alarm) : Long

    @Delete
    suspend fun delete(alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Query("DELETE FROM alarms_list_table")
    suspend fun clear()

    @Query("SELECT * FROM alarms_list_table ORDER BY hour ASC")
    fun getAlarmsList(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarms_list_table WHERE id = :id")
    suspend fun getAlarmById(id: Int): Alarm?

    @Query("SELECT id FROM alarms_list_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Long?

    @Query("SELECT * FROM alarms_list_table WHERE medicationId = :medicationId ORDER BY hour ASC, minute ASC")
    fun getAlarmsForMedication(medicationId: Int): Flow<List<Alarm>>

    @Query("SELECT * FROM alarms_list_table WHERE medicationId = :medicationId ORDER BY hour ASC, minute ASC")
    suspend fun getAlarmsForMedicationOnce(medicationId: Int): List<Alarm>

    @Query("SELECT * FROM alarms_list_table WHERE medicationId = :medicationId AND hour = :hour AND minute = :minute")
    fun getAlarmByTime(medicationId: Int, hour: String, minute: String): Flow<Alarm?>

}