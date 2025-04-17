package com.hfad.auth.ui.mvi

sealed class AuthIntents {
    data class OnPhoneEntered(val phone: String ) : AuthIntents()
    data class OnCodeEntered(val code: String): AuthIntents()
    data class OnProfileCompleted(val name: String, val email: String): AuthIntents()

}