package com.hfad.main.ui.mvi.pass

import android.graphics.Bitmap

data class PdfState(
    val isLoading: Boolean = true,
    val bitmaps: List<Bitmap> = emptyList(),
    val error: String? = null
)
