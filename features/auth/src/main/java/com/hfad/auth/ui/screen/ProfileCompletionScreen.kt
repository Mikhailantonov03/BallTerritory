package com.hfad.auth.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.auth.ui.mvi.UiStatus

@Composable
fun ProfileCompletionScreen(
    uiState: AuthUiState,
    onComplete: (String, String) -> Unit // имя + email
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val isFormValid = name.isNotBlank() && email.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Завершите регистрацию")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Введите email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onComplete(name, email) },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Продолжить")
        }

        when (val status = uiState.status) {
            is UiStatus.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            is UiStatus.Error -> Text(
                text = status.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
            else -> Unit
        }
    }
}
