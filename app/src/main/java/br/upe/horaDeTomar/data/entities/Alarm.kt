package br.upe.horaDeTomar.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(
    tableName = "alarms_list_table",
    foreignKeys = [ForeignKey(
        entity = Medication::class,
        parentColumns = ["id"],
        childColumns = ["medicationId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(
            value = [
                "hour", "minute", "daysSelectedJson",
            ],
            unique = true,
        )
    ]
)
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var hour: String = "00",
    var minute: String = "00",
    var isScheduled: Boolean = true,
    var daysSelectedJson: String = Gson().toJson(
        mapOf(
            "Dom" to false,
            "Seg" to false,
            "Ter" to false,
            "Qua" to false,
            "Qui" to false,
            "Sex" to false,
            "Sab" to false
        ),
    ),
    var medicationId: Int,
) {
    val daysSelected: Map<String, Boolean>
        get() = Gson().fromJson(daysSelectedJson, object : TypeToken<Map<String, Boolean>>() {}.type)

    fun setDaysSelected(days: Map<String, Boolean>) {
        this.daysSelectedJson = Gson().toJson(days)
    }
}