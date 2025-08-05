package com.hfad.profile.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.hfad.profile.R

@Composable
fun AboutScreen() {
    val imageIds = listOf(
        R.drawable.center_photo_4,
        R.drawable.center_photo_1,
        R.drawable.center_photo_2,
        R.drawable.center_photo_3
    )

    var isVisible by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isVisible = true
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    AnimatedVisibility(visible = isVisible, enter = fadeIn()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            SectionHeader("О нашем центре")

            AboutParagraph("«Территория Мяча» — это современный спортивный центр, где каждый найдёт себе занятие по душе. У нас проходят тренировки по футболу, баскетболу, волейболу и другим видам спорта. Просторные залы, опытные тренеры и дружественная атмосфера ждут вас!")

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SectionHeader("Расположение")

            AboutParagraph("В пределах ТТК, в пешей доступности от метро Дубровка и Волгоградский проспект, а также МЦК Угрешская, мы создали комфортные спортивные условия. В «Территории мяча» найдётся место для всех — 7 площадок способны вместить всех желающих на тренировках, играх или турнирах.")

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SectionHeader("Тренировки и развитие")

            AboutParagraph("Мы всегда готовы помочь вам развить спортивные и игровые навыки. Наши двери открыты для проведения как групповых, так и индивидуальных тренировок. Под руководством наших профессиональных тренеров вы сможете улучшить свои умения.")

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SectionHeader("Инфраструктура")

            AboutParagraph("Игровые площадки оснащены всем необходимым спортивным оборудованием. 4300 м² качественного паркета, комфортные раздевалки с душевыми и уютное кафе создают атмосферу, в которой каждый участник чувствует удовольствие от проведённого времени в нашем спортивном центре.")

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader("Фотографии центра")

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(imageIds) { imageRes ->
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .width(220.dp)
                            .aspectRatio(4f / 3f)
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Фото центра",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun AboutParagraph(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
        lineHeight = 22.sp,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}
