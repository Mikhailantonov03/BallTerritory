package com.hfad.schedule.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.schedule.ui.model.ScheduleItem
import com.hfad.schedule.ui.mvi.Schedule.toFormattedDate
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardIntent
import com.hfad.schedule.ui.mvi.ScheduleCard.ScheduleCardUiState
import com.hfad.ui.SessionState

@Composable
fun ScheduleCard(
    item: ScheduleItem,
    isBooked: Boolean,
    sessionState: SessionState,
    cardState: ScheduleCardUiState,
    onIntent: (ScheduleCardIntent) -> Unit,
    onSignUp: (String) -> Unit,
    onCancel: (String) -> Unit,
    onLoginRequired: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF272933))
            .clickable { onIntent(ScheduleCardIntent.ToggleExpand) }
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.timestamp.toFormattedDate(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = item.trainer,
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                when {
                    sessionState.isFullyAuthorized && isBooked -> {
                        OutlinedButton(
                            onClick = { onIntent(ScheduleCardIntent.ShowCancelDialog) },
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text("Отменить")
                        }
                    }

                    sessionState.isFullyAuthorized -> {
                        Button(
                            onClick = { onIntent(ScheduleCardIntent.ShowSignUpDialog) },
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEA5C3D),
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Записаться",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    else -> {
                        OutlinedButton(
                            onClick = onLoginRequired,
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text("Войти")
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = cardState.isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = shrinkVertically() + fadeOut()
            ) {
                val sport = item.title.substringBefore(":").lowercase()
                val sportInDative = when (sport) {
                    "баскетбол" -> "баскетболу"
                    "волейбол" -> "волейболу"
                    "фитнес" -> "фитнесу"
                    "бадминтон" -> "бадминтону"
                    "стретчинг" -> "стретчингу"
                    "стритбол" -> "стритболу"
                    "йога" -> "йоге"
                    else -> "${sport}у"
                }

                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "Описание: Тренировка по $sportInDative, отлично подходит для всех уровней подготовки.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Длительность: ${item.durationMinutes} минут",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    if (cardState.showSignUpDialog) {
        AlertDialog(
            onDismissRequest = { onIntent(ScheduleCardIntent.HideDialogs) },
            title = { Text("Подтвердите запись") },
            text = { Text("Вы уверены, что хотите записаться на «${item.title}»?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onSignUp(item.id)
                        onIntent(ScheduleCardIntent.HideDialogs)
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF9800))
                ) {
                    Text("Подтвердить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onIntent(ScheduleCardIntent.HideDialogs) },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF9800))
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    if (cardState.showCancelDialog) {
        AlertDialog(
            onDismissRequest = { onIntent(ScheduleCardIntent.HideDialogs) },
            title = { Text("Отменить запись") },
            text = { Text("Вы действительно хотите отменить запись на «${item.title}»?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCancel(item.id)
                        onIntent(ScheduleCardIntent.HideDialogs)
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF9800))
                ) {
                    Text("Да, отменить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onIntent(ScheduleCardIntent.HideDialogs) },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF9800))
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}
