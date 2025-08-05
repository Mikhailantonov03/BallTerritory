package com.hfad.main.ui.util.pass

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun renderPdf(context: Context, file: File): List<Bitmap> = withContext(Dispatchers.IO) {
    val result = mutableListOf<Bitmap>()
    val fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val renderer = PdfRenderer(fd)

     val scale = 4

    for (i in 0 until renderer.pageCount) {
        val page = renderer.openPage(i)

        val width = page.width * scale
        val height = page.height * scale

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

         val matrix = android.graphics.Matrix().apply {
            postScale(scale.toFloat(), scale.toFloat())
        }

        page.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        result.add(bitmap)
        page.close()
    }

    renderer.close()
    fd.close()
    return@withContext result
}
