package com.hfad.main.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

suspend fun downloadPdfFile(context: Context, url: String): File = withContext(Dispatchers.IO) {
    val input = URL(url).openStream()
    val file = File(context.cacheDir, "pass.pdf")
    FileOutputStream(file).use { output -> input.copyTo(output) }
    file
}
