package com.hfad.auth.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.module.UiStatus
import kotlinx.coroutines.delay

@Composable
fun EnterPhoneScreen(
    uiState: AuthUiState,
    onPhoneEntered: (String) -> Unit,
    onNavigateToCode: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "alpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.95f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "scale"
    )

    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход или\nрегистрация",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .alpha(alpha)
                .scale(scale)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { input -> phone = input.filter { it.isDigit() }.take(10) },
            singleLine = true,
            leadingIcon = { Text("+7", fontSize = 16.sp) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFDED6E9),
                unfocusedContainerColor = Color(0xFFDED6E9),
                cursorColor = Color.Black
            ),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            placeholder = { Text("number", color = Color.Gray) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .alpha(alpha)
                .scale(scale)
        )

        Text(
            text = "Введите номер",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, bottom = 24.dp)
                .alpha(alpha)
                .scale(scale)
        )

        val isEnabled = phone.length == 10
        val buttonAlpha by animateFloatAsState(
            targetValue = if (isEnabled) 1f else 0.5f,
            animationSpec = tween(durationMillis = 300),
            label = "buttonAlpha"
        )

        Button(
            onClick = { if (isEnabled) onPhoneEntered("+7$phone") },
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(buttonAlpha)
                .scale(scale)
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6542),
                disabledContainerColor = Color(0x33FF6542),
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            )
        ) {
            Text("Получить код", fontSize = 16.sp)
        }

        Text(
            text = "Вводя номер, ты соглашаешься с политикой обработки\nперсональных данных и пользовательским соглашением",
            fontSize = 10.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .alpha(alpha)
                .scale(scale)
        )

        when (uiState.status) {
            is UiStatus.Loading -> {
                CircularProgressIndicator(
                    color = Color(0xFFFF9800),
                    trackColor = Color(0x33FFFFFF),
                    strokeWidth = 6.dp,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(36.dp)
                )
            }

            is UiStatus.Error -> {
                Text(
                    text = uiState.status.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            else -> Unit
        }

        if (uiState.phoneSent) {
            LaunchedEffect(Unit) {
                onNavigateToCode()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnterPhoneScreenPreview() {
    EnterPhoneScreen(
        uiState = AuthUiState(),
        onPhoneEntered = {},
        onNavigateToCode = {}
    )
}
