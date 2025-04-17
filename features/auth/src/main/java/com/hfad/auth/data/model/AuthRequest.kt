package com.hfad.auth.data.model



data class PhoneRequest(
    val phone: String
)

data class CodeRequest(
    val phone: String,
    val code: String
)
data class ProfileRequest(
    val name: String,
    val email: String
)
