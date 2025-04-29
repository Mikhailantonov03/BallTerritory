package com.hfad.module

sealed class UiStatus {
    object Idle : UiStatus()
    object Loading : UiStatus()
    data class Error(val message: String) : UiStatus()
}
