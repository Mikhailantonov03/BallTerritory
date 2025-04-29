package com.hfad.profile.ui.mvi.history

import com.hfad.profile.history.HistoryItem

data class HistoryState(
    val history: List<HistoryItem> = emptyList()
)
