package br.upe.horaDeTomar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import br.upe.horaDeTomar.data.entities.Account
import br.upe.horaDeTomar.data.entities.User
import br.upe.horaDeTomar.data.entities.Medication

import br.upe.horaDeTomar.data.daos.AccountDao
import br.upe.horaDeTomar.data.daos.AlarmDao
import br.upe.horaDeTomar.data.daos.UserDao
import br.upe.horaDeTomar.data.daos.MedicationDao
import br.upe.horaDeTomar.data.entities.Alarm

@Database(
    entities = [Account::class, User::class, Medication::class, Alarm::class], version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun userDao(): UserDao
    abstract fun medicationDao(): MedicationDao
    abstract fun alarmDao(): AlarmDao
}
