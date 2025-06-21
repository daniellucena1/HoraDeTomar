package br.upe.horaDeTomar.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.upe.horaDeTomar.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE accountId = :accountId")
    suspend fun getUsersByAccount(accountId: Int): List<User>?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}
