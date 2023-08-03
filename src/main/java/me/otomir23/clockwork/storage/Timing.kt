package me.otomir23.clockwork.storage

import kotlinx.serialization.Serializable

@Serializable
data class Timing(
    val name: String,
    val delay: Long
)