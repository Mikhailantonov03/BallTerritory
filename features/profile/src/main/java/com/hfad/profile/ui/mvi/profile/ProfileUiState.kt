package com.hfad.profile.ui.mvi.profile



import com.hfad.module.User
import com.hfad.module.UiStatus

data class ProfileUiState(
    val user: User? = null,
    val status: UiStatus = UiStatus.Idle,
    val logoutEvent: Boolean = false,
    val isEditing: Boolean = false

)
