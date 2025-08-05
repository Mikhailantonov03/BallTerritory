package com.hfad.main.ui.mvi.home

sealed interface HomeIntent {
    data object LoadArticles : HomeIntent
    data class OnArticleClicked(val url: String) : HomeIntent
    data object OnPassClicked : HomeIntent
}
