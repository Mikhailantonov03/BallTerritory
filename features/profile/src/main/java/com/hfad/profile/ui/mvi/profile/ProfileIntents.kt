package com.hfad.profile.ui.mvi.profile

sealed class ProfileIntents {
    object LoadProfile : ProfileIntents()
    object OnLogoutClicked : ProfileIntents()
    object OnLogoutHandled : ProfileIntents()
    object OnEditRequested : ProfileIntents()
    data class OnEditSubmitted(val name: String, val email: String) : ProfileIntents()
    object OnEditDismissed : ProfileIntents()
}
