package com.hfad.auth.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.auth.ui.mvi.UiStatus

@Composable
fun ProfileCompletionScreen(
    uiState: AuthUiState,
    onComplete: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Завершите регистрацию")

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onComplete(name) }) {
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
