package com.hfad.main.domain.usecase

import android.graphics.Bitmap
import com.hfad.main.domain.repository.PdfRepository
import javax.inject.Inject

class LoadPdfUseCase @Inject constructor(
    private val repository: PdfRepository
) {
    suspend operator fun invoke(url: String): List<Bitmap> {
        return repository.getPdfBitmaps(url)
    }
}
