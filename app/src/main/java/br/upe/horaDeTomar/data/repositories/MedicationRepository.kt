package br.upe.horaDeTomar.data.repositories

import br.upe.horaDeTomar.data.daos.MedicationDao
import br.upe.horaDeTomar.data.entities.Medication
import br.upe.horaDeTomar.data.entities.User
import kotlinx.coroutines.flow.Flow

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

    suspend fun getAllMediactions(): List<Medication>? {
        return dao.getAllMedications()
    }

    val medications: Flow<List<Medication>> = dao.getMedications();
}