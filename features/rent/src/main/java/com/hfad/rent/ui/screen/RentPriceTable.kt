package com.hfad.rent.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.rent.ui.mvi.PriceEntry

@Composable
fun RentPriceTable(hallName: String?, prices: List<PriceEntry>) {
    if (hallName == null) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF272933))
            .padding(16.dp)
    ) {
        Text(
            text = hallName,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Заголовок таблицы
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Время", color = Color.LightGray, fontWeight = FontWeight.Bold)
            Text("Будни", color = Color.LightGray, fontWeight = FontWeight.Bold)
            Text("Выходные", color = Color.LightGray, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        prices.forEach { entry ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(entry.timeRange, color = Color.White)
                Text(entry.weekdayPrice, color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
                Text(entry.weekendPrice, color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Указана стоимость аренды за 1 час для тренировок. Инвентарь не входит в стоимость. Для мероприятий цена обсуждается отдельно.",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}
