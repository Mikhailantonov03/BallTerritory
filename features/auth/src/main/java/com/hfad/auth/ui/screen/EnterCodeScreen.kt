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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.module.UiStatus
import kotlinx.coroutines.delay

@Composable
fun EnterCodeScreen(
    uiState: AuthUiState,
    onCodeEntered: (String) -> Unit,
    onNavigateToProfileSetup: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
        delay(300) // дождись окончания анимации
        focusRequester.requestFocus()
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "alpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.95f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "scale"
    )

    if (uiState.requiresProfileCompletion) {
        LaunchedEffect(Unit) {
            onNavigateToProfileSetup()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .alpha(alpha)
                .scale(scale)
        ) {
            repeat(4) { index ->
                Surface(
                    modifier = Modifier.size(60.dp),
                    color = Color(0xFF272933),
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = code.getOrNull(index)?.toString() ?: "",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        TextField(
            value = code,
            onValueChange = { input -> code = input.filter { it.isDigit() }.take(4) },
            modifier = Modifier
                .alpha(0f)
                .fillMaxWidth()
                .height(1.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Transparent,
                unfocusedTextColor = Color.Transparent,
                cursorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = { onCodeEntered(code) },
            enabled = code.length == 4,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .alpha(alpha)
                .scale(scale),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6542),
                disabledContainerColor = Color(0x33FF6542)
            )
        ) {
            Text("Отправить код", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState.status) {
            is UiStatus.Loading -> {
                CircularProgressIndicator(
                    color = Color(0xFFFF9800), // ярко-оранжевый
                    trackColor = Color(0x33FFFFFF), // полупрозрачный белый
                    strokeWidth = 6.dp,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }

            is UiStatus.Error -> {
                Text(
                    text = uiState.status.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            else -> Unit
        }
    }
}
