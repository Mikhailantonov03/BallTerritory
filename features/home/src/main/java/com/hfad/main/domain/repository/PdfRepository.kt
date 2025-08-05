package com.hfad.main.domain.repository

import android.graphics.Bitmap

interface PdfRepository {
    suspend fun getPdfBitmaps(url: String): List<Bitmap>
}
