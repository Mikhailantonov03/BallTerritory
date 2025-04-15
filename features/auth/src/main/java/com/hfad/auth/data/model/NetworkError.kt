package com.hfad.auth.data.model

sealed class NetworkError : Throwable() {
    object NetworkConnectionError : NetworkError()
    object ApiError : NetworkError()
    data class UnknownError( override val message: String) : NetworkError()
}
