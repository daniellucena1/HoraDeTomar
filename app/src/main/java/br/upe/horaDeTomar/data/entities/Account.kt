package br.upe.horaDeTomar.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val createdAt: Long,
    val accountName: String,
    // TODO: Adicionar lista de usuários
)