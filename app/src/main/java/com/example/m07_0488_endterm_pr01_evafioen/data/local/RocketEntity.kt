package com.example.m07_0488_endterm_pr01_evafioen.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val country: String?,
    val description: String?,
    val imageUrl: String?,
    val active: Boolean,

    // Campos para la pantalla de detalle
    val firstFlight: String?,
    val costPerLaunch: Double?,
    val successRatePct: Double?,
    val wikipedia: String?
)
