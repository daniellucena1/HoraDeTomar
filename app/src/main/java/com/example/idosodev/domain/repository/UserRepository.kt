package com.example.idosodev.domain.repository

import com.example.idosodev.domain.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: String): User?
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
}