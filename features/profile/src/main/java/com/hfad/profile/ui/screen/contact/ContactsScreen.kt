package com.hfad.profile.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContactsScreen() {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(horizontal = 24.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Контакты",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Телефон
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                copyToClipboard(context, clipboardManager, "+7 495 055-13-42")
            }
        ) {
            Icon(Icons.Default.Phone, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "+7 495 055-13-42",
                color = Color(0xFFFF6542),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Почта
        Text(
            text = "inbox@terball.ru",
            color = Color(0xFFFF6542),
            fontSize = 18.sp,
            modifier = Modifier.clickable {
                copyToClipboard(context, clipboardManager, "inbox@terball.ru")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Адрес
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.clickable {
                copyToClipboard(
                    context,
                    clipboardManager,
                    "Москва, ул. Шарикоподшипниковская, 13 стр. 24"
                )
            }
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Москва, ул. Шарикоподшипниковская, 13 стр. 24",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text(
                    text = "Вход со 2-ой улицы Машиностроения рядом с домом 23",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Платная парковка.",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // График работы
        Text(
            text = "График работы",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        val days = listOf(
            "Понедельник", "Вторник", "Среда",
            "Четверг", "Пятница", "Суббота", "Воскресенье"
        )
        days.forEach {
            Text(
                text = "$it: Круглосуточно",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ссылки на карты
        Text(
            text = "Посмотреть адрес:",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        MapLink("в Яндекс.Картах", "https://yandex.ru/maps/-/CDb~ENFg")
        MapLink("в Google.Maps", "https://maps.google.com/?q=55.708333,37.664444")
        MapLink("в 2Gis", "https://2gis.ru/moscow/geo/70030001072265815")

        Spacer(modifier = Modifier.height(28.dp))

        // Соцсети
        Text(
            text = "@terball в соцсетях",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MapLink("vk", "https://vk.com/terball")
            MapLink("youtube", "https://youtube.com/@terball")
        }
    }
}

@Composable
private fun MapLink(text: String, url: String) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xFFFF6542),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(text)
            }
        },
        fontSize = 15.sp,
        modifier = Modifier.clickable {
            uriHandler.openUri(url)
        }
    )
}

private fun copyToClipboard(
    context: Context,
    clipboardManager: androidx.compose.ui.platform.ClipboardManager,
    text: String
) {
    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(text))
    android.widget.Toast
        .makeText(context, "Скопировано: $text", android.widget.Toast.LENGTH_SHORT)
        .show()
}
