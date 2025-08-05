package com.hfad.main.ui.mvi.home

sealed interface HomeSingleEvent {
    data object NavigateToPass : HomeSingleEvent
    data class NavigateToArticle(val url: String) : HomeSingleEvent
}
