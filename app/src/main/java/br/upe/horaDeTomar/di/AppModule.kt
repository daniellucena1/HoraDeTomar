package br.upe.horaDeTomar.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.db.SupportSQLiteDatabase
import br.upe.horaDeTomar.data.AppDatabase
import br.upe.horaDeTomar.data.daos.UserDao
import br.upe.horaDeTomar.data.repositories.MedicationRepository
import br.upe.horaDeTomar.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hora_de_tomar.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db : SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val database = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "hora_de_tomar.db"
                    ).build()
                    kotlinx.coroutines.GlobalScope.launch {
                        database.accountDao().insert(
                            br.upe.horaDeTomar.data.entities.Account(
                                id = 1,
                                createdAt = System.currentTimeMillis(),
                                accountName = "Default Account",
                            )
                        )
                    }
                }
            })
            .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    fun provideMedicationDao(db: AppDatabase): br.upe.horaDeTomar.data.daos.MedicationDao {
        return db.medicationDao()
    }

    @Provides
    fun provideMedicationRepository(dao: br.upe.horaDeTomar.data.daos.MedicationDao): br.upe.horaDeTomar.data.repositories.MedicationRepository {
        return MedicationRepository(dao)
    }
}
