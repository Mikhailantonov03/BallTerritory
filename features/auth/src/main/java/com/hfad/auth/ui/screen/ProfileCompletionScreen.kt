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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.module.UiStatus
import kotlinx.coroutines.delay

@Composable
fun ProfileCompletionScreen(
    uiState: AuthUiState,
    onComplete: (String, String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val isEmailValid = remember(email) {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    val isFormValid = name.isNotBlank() && isEmailValid

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
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

    val buttonScale by animateFloatAsState(
        targetValue = if (isFormValid) 1f else 0.98f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "buttonScale"
    )

    val buttonAlpha by animateFloatAsState(
        targetValue = if (isFormValid) 1f else 0.5f,
        animationSpec = tween(durationMillis = 300),
        label = "buttonAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Завершите регистрацию",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .alpha(alpha)
                .scale(scale)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            placeholder = { Text("Имя", color = Color.LightGray) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFF2E303D),
                unfocusedContainerColor = Color(0xFF2E303D),
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .alpha(alpha)
                .scale(scale)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            isError = email.isNotBlank() && !isEmailValid,
            singleLine = true,
            placeholder = { Text("Email", color = Color.LightGray) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (email.isNotBlank() && !isEmailValid) Color.Red else Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFF2E303D),
                unfocusedContainerColor = Color(0xFF2E303D),
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
                .scale(scale)
        )

        if (email.isNotBlank() && !isEmailValid) {
            Text(
                text = "Введите корректный email",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp, top = 4.dp, bottom = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = { onComplete(name, email) },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .scale(buttonScale)
                .alpha(buttonAlpha),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6542),
                disabledContainerColor = Color(0x33FF6542),
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            )
        ) {
            Text("Продолжить", fontSize = 16.sp)
        }

        when (val status = uiState.status) {
            is UiStatus.Loading -> CircularProgressIndicator(
                color = Color(0xFFFF9800),
                trackColor = Color(0x33FFFFFF),
                strokeWidth = 6.dp,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(36.dp)
            )

            is UiStatus.Error -> Text(
                text = status.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
        }
    }
}
