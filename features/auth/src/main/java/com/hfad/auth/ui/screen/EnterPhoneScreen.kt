package com.hfad.auth.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.auth.ui.mvi.UiStatus

@Composable
fun EnterPhoneScreen(
    uiState: AuthUiState,
    onPhoneEntered: (String) -> Unit,
    onNavigateToCode: () -> Unit
) {
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Введите номер телефона") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onPhoneEntered(phone) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отправить код")
        }

        when (uiState.status) {
            is UiStatus.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            is UiStatus.Error -> {
                Text(
                    text = (uiState.status ).message,
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
    MaterialTheme {
        EnterPhoneScreen(
            uiState = AuthUiState(),
            onPhoneEntered = {},
            onNavigateToCode = {}
        )
    }
}
