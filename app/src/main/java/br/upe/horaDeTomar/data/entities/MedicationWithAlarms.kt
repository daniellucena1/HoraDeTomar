package br.upe.horaDeTomar.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MedicationWithAlarms(
    @Embedded val medication: Medication,
    @Relation(
        parentColumn = "id",
        entityColumn = "medicationId"
    )
    val alarms: List<Alarm>
)
