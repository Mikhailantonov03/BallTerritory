package com.hfad.main.ui.screen.map

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun FullMapScreen() {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                webViewClient = WebViewClient()
                loadUrl("https://yandex.ru/maps/?um=constructor%3Aa2b560e4e11e7fce72e9f2cdc24c813d3a117c1cbdcd81fe57de90bf4ee98258&source=constructorLink") // Та же карта, но на весь экран
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
