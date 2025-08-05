package com.hfad.rent.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.rent.ui.mvi.RentIntent
import com.hfad.rent.ui.viewModel.RentViewModel
import com.hfad.ui.SessionStateViewModel

@Composable
fun RentScreen(viewModel: RentViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Аренда залов",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = uiState.selectedHallType ?: "Выбери",
                        color = Color.White
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    uiState.hallTypes.forEach { hall ->
                        DropdownMenuItem(
                            text = { Text(hall) },
                            onClick = {
                                viewModel.onIntent(RentIntent.SelectHallType(hall))
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (uiState.selectedHallType == null) {
            item {
                RentWelcomeBlock()
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (uiState.selectedHallType != null) {
            val selected = uiState.selectedHallType

            item {
                val images = uiState.hallImages[selected] ?: emptyList()
                if (images.isNotEmpty()) {
                    HallImageCarousel(images = images)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            item {
                Button(
                    onClick = { showConfirmationDialog = true },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEA5C3D),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Оставить заявку")
                }

                if (uiState.errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
                }

                if (uiState.isRequestSent) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Заявка отправлена!", color = Color.Green)
                }

                if (uiState.needsAuth) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Чтобы оставить заявку, пожалуйста, авторизуйтесь.",
                        color = Color.Yellow
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                val prices = uiState.hallPrices[selected] ?: emptyList()
                RentPriceTable(hallName = selected, prices = prices)
            }
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Подтверждение") },
            text = { Text("Вы точно хотите оставить заявку?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmationDialog = false
                        viewModel.onIntent(RentIntent.SubmitRequest)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmationDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Нет")
                }
            }
        )
    }
}
