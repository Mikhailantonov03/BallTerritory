package com.hfad.auth.ui.screen


import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun StartScreen(onStart: () -> Unit,
                onSkipClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Территория мяча", modifier = Modifier.padding(bottom = 32.dp))

        Button(onClick = onStart) {
            Text("Продолжить")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSkipClick) {
            Text("Пропустить")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    MaterialTheme {
        StartScreen(onStart = {}, onSkipClick = {})
    }
}

