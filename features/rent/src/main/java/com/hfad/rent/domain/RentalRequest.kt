package com.hfad.rent.domain

data class RentalRequest(
    val hallName: String,
    val userId: String,
    val userName: String?,
    val userPhone: String,
    val userEmail: String?
)
