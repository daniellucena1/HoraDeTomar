package br.upe.horaDeTomar.data.repositories

import br.upe.horaDeTomar.data.daos.MedicationDao
import br.upe.horaDeTomar.data.entities.Medication

class MedicationRepository(private val dao: MedicationDao) {

    suspend fun insert(medication: Medication): Long {
        return dao.insert(medication)
    }

    suspend fun update(medication: Medication) {
        dao.update(medication)
    }

    suspend fun delete(medication: Medication) {
        dao.delete(medication)
    }

    suspend fun getByUser(userId: Int): List<Medication>? {
        return dao.getMedicationsByUser(userId)
    }

    suspend fun getById(id: Int): Medication? {
        return dao.getMedicationById(id)
    }
}