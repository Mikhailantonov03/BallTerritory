package com.hfad.rent.data.api

data class RentalRequestDto(
    val hallName: String,
    val userId: String,
    val userName: String?,
    val userPhone: String,
    val userEmail: String?
)
