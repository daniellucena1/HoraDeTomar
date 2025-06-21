package br.upe.horaDeTomar.data

import androidx.room.Database
import androidx.room.RoomDatabase

import br.upe.horaDeTomar.data.entities.Account
import br.upe.horaDeTomar.data.entities.User
import br.upe.horaDeTomar.data.entities.Medication

import br.upe.horaDeTomar.data.daos.AccountDao
import br.upe.horaDeTomar.data.daos.UserDao
import br.upe.horaDeTomar.data.daos.MedicationDao

@Database(
    entities = [Account::class, User::class, Medication::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun userDao(): UserDao
    abstract fun medicationDao(): MedicationDao
}
