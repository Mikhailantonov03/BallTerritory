package com.hfad.profile.ui.mvi.history

sealed interface HistoryIntent {
    data object LoadHistory : HistoryIntent
}
