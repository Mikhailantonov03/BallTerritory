package com.hfad.main.ui.mvi.pass

sealed interface PdfIntent {
    data class LoadPdf(val url: String) : PdfIntent
}
