package com.hfad.profile.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SecondaryOptionsBlock(
    onHistoryClick: () -> Unit,
    onContactsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF2B2C30))
            .padding(vertical = 8.dp)
    ) {
        ProfileOption("История тренировок", onHistoryClick)
        ProfileOption("Контакты", onContactsClick)
        ProfileOption("О центре", onAboutClick)
    }
}