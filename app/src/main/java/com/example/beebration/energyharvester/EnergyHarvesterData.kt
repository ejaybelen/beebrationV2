package com.example.beebration.energyharvester

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EnergyHarvesterData(
    val timestamp: Long,
    val voltage: Float,
    val current: Float
)