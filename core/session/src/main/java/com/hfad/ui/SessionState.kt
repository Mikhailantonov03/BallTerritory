package com.hfad.ui

data class SessionState(
    val isLoggedIn: Boolean = false,
    val isGuest: Boolean = false
) {
    val isFullyAuthorized: Boolean get() = isLoggedIn && !isGuest
}
