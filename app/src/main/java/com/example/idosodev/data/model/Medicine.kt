package com.example.idosodev.data.model

data class Medicine(
    val id: String,
    val name: String,
    val dosage: String,
    val via: String,
    val period: String,
    val time: String? = null
)
