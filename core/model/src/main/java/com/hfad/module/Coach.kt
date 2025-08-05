package com.hfad.module

import kotlinx.serialization.Serializable

@Serializable
data class Coach(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val sport: SportType,
    val bio: String
)

enum class SportType {
    BASKETBALL, VOLLEYBALL
}
