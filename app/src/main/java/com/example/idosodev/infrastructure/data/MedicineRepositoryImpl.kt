package com.example.idosodev.infrastructure.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.idosodev.domain.model.Medicine
import com.example.idosodev.domain.repository.MedicineRepository

class MedicineRepositoryImpl: MedicineRepository {
    private val medicines = mutableListOf(
        Medicine("m1", "Paracetamol", "Dosagem: 1 comprimido","Oral", " 2x ao dia", "Próxima dose: 10:00"),
        Medicine("m2", "Omeprazol", "Dosagem: 20mg", "Oral", " 1x ao dia antes do café", "Próxima dose: 08:00")
    )

    override suspend fun getMedicines(): List<Medicine> {
        return medicines
    }

    override suspend fun getMedicineById(medicineId: String): Medicine? {
        return medicines.find { it.id == medicineId }
    }

    override suspend fun addMedicine(medicine: Medicine) {
        medicines.add(medicine)
    }

    override suspend fun updateMedicine(medicine: Medicine) {
        val index = medicines.indexOfFirst { it.id == medicine.id }
        if (index != -1) {
            medicines[index] = medicine
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun deleteMedicine(medicineId: String) {
        medicines.removeIf { it.id == medicineId }
    }

    override suspend fun getUpcomingMedicines(): List<Medicine> {
        // Lógica para filtrar remédios próximos de serem tomados
        // Por enquanto, retorna todos, mas aqui entraria a lógica de agendamento
        return medicines
    }
}