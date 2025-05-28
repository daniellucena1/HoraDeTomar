package com.example.idosodev.domain.repository

import com.example.idosodev.domain.model.Medicine

interface MedicineRepository {
    suspend fun getMedicines(): List<Medicine>
    suspend fun getMedicineById(medicineId: String): Medicine?
    suspend fun addMedicine(medicine: Medicine)
    suspend fun updateMedicine(medicine: Medicine)
    suspend fun deleteMedicine(medicineId: String)
    suspend fun getUpcomingMedicines(): List<Medicine>
}