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
fun EnterCodeScreen(
    uiState: AuthUiState,
    onCodeEntered: (String) -> Unit,
    onNavigateToProfileSetup: () -> Unit
) {
    var code by remember { mutableStateOf("") }

    LaunchedEffect(uiState.requiresProfileCompletion) {
        if (uiState.requiresProfileCompletion) {
            onNavigateToProfileSetup()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Введите код из СМС") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onCodeEntered(code) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Подтвердить")
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

        if (uiState.accessToken != null) {
            Text(
                text = "Авторизация успешна!\nToken: ${uiState.accessToken}",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnterCodeScreenPreview() {
    MaterialTheme {
        EnterCodeScreen(
            uiState = AuthUiState(),
            onCodeEntered = {},
            onNavigateToProfileSetup = {}
        )
    }
}
