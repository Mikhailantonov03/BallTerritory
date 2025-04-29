package com.hfad.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
 import com.hfad.profile.ui.mvi.profile.ProfileIntents
import com.hfad.profile.ui.screen.profile.EditProfileContent
import com.hfad.profile.ui.viewModel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLoggedOut: () -> Unit,
    onHistoryClick: () -> Unit,
    onSocialsClick: () -> Unit,
    onContactsClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ProfileIntents.LoadProfile)
    }

    LaunchedEffect(state.logoutEvent) {
        if (state.logoutEvent) {
            onLoggedOut()
            viewModel.onIntent(ProfileIntents.OnLogoutHandled)
        }
    }

    val user = state.user

    user?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Имя: ${it.name ?: "—"}", style = MaterialTheme.typography.titleLarge)
            Text("Телефон: ${it.phone}")
            Text("Email: ${it.email ?: "—"}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.onIntent(ProfileIntents.OnEditRequested)
            }) {
                Text("Редактировать")
            }

            Spacer(modifier = Modifier.height(24.dp))

            SecondaryOptionsBlock(
                onHistoryClick = onHistoryClick,
                onSocialsClick = onSocialsClick,
                onContactsClick = onContactsClick,
                onAboutClick = onAboutClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                viewModel.onIntent(ProfileIntents.OnLogoutClicked)
            }) {
                Text("Выйти")
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    if (state.isEditing && user != null) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onIntent(ProfileIntents.OnEditDismissed)
            }
        ) {
            EditProfileContent(
                initialName = user.name ?: "",
                initialEmail = user.email ?: "",
                onSave = { name, email ->
                    viewModel.onIntent(ProfileIntents.OnEditSubmitted(name, email))
                },
                onCancel = {
                    viewModel.onIntent(ProfileIntents.OnEditDismissed)
                }
            )
        }
    }
}

@Composable
fun SecondaryOptionsBlock(
    onHistoryClick: () -> Unit,
    onSocialsClick: () -> Unit,
    onContactsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp)
    ) {
        ProfileOption("История тренировок", onHistoryClick)
        ProfileOption("Наши соцсети", onSocialsClick)
        ProfileOption("Контакты", onContactsClick)
        ProfileOption("О центре", onAboutClick)
    }
}

@Composable
fun ProfileOption(text: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(text) },
        trailingContent = {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
}
