package com.hfad.main.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.hfad.main.data.downloadPdfFile
import com.hfad.main.domain.repository.PdfRepository
import com.hfad.main.ui.util.pass.renderPdf
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PdfRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PdfRepository {

    private val bitmapCache = mutableMapOf<String, List<Bitmap>>()

    override suspend fun getPdfBitmaps(url: String): List<Bitmap> {
        bitmapCache[url]?.let { return it }

        val file = downloadPdfFile(context, url)
        val bitmaps = renderPdf(context, file)

        bitmapCache[url] = bitmaps
        return bitmaps
    }
}
