package br.upe.horaDeTomar.ui.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(private val repository: MedicationRepository): ViewModel() {
    val medications: StateFlow<List<Medication>> = repository.medications
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    suspend fun createMedication(
        name: String,
        via: String,
        dose: String,
        period: String,
        hour: String,
        minute: String,
        selectedDays: List<String>
    ) {
        val medication = Medication(
            name = name,
            via = via,
            dose = dose,
            period = period,
            time = "$hour:$minute",
            selectedDays = selectedDays.joinToString(","),
            userId = 1 // Assumindo uma user ID fixa para simplificação
        )
        repository.insert(medication)
    }
}