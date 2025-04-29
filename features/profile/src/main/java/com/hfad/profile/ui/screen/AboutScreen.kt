package com.hfad.profile.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfad.profile.R

@Composable
fun AboutScreen() {
    val imageIds = listOf(
        R.drawable.center_photo_1,
        R.drawable.center_photo_2,
        R.drawable.center_photo_3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "О нашем центре",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Территория Мяча — это современный спортивный центр, где каждый может найти себе занятие по душе. У нас проводятся тренировки по футболу, баскетболу, волейболу и другим видам спорта. Просторные залы, опытные тренеры и дружественная атмосфера ждут вас!",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Фотографии центра",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(imageIds) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Фото центра",
                    modifier = Modifier
                        .size(200.dp)
                        .aspectRatio(4f / 3f),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
