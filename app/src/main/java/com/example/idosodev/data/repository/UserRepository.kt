package com.example.idosodev.data.repository

import com.example.idosodev.data.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: String): User?
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
}