package com.example.idosodev.infrastructure.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.idosodev.domain.model.User
import com.example.idosodev.domain.repository.UserRepository

class UserRepositoryImpl: UserRepository {
    private val users = mutableListOf(
        User("1", "Daniel Torres", "Rua Alcides", "15/04/2004", "Última Atualização: 20/05/2025"),
        User("2", "Vinicius Menezes", "Nova Caruaru", "15/04/2004", "Última Atualização: 20/05/2025")
    )

    override suspend fun getUsers(): List<User> {
        return users
    }

    override suspend fun getUserById(userId: String): User? {
        return users.find { it.id == userId }
    }

    override suspend fun addUser(user: User) {
        users.add(user)
    }

    override suspend fun updateUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun deleteUser(userId: String) {
        users.removeIf { it.id == userId }
    }
}