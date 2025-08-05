package com.hfad.profile.ui.screen.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hfad.profile.R
import com.hfad.profile.ui.mvi.profile.ProfileIntents
import com.hfad.profile.ui.viewModel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLoggedOut: () -> Unit,
    onHistoryClick: () -> Unit,
    onContactsClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var showContent by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(ProfileIntents.LoadProfile)
        showContent = true
    }

    LaunchedEffect(state.logoutEvent) {
        if (state.logoutEvent) {
            onLoggedOut()
            viewModel.onIntent(ProfileIntents.OnLogoutHandled)
        }
    }

    val user = state.user

    if (user == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F2129)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFFFF9800),
                trackColor = Color(0x33FFFFFF),
                strokeWidth = 6.dp
            )
        }
        return
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color(0xFF1F2129)
    ) { innerPadding ->

        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFF1F2129))
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_camera),
                        contentDescription = "Avatar",
                        modifier = Modifier.size(120.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = user.name ?: "—",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = user.phone, color = Color.White)
                user.email?.let { Text(it, color = Color.Gray) }

                Spacer(modifier = Modifier.height(32.dp))

                SecondaryOptionsBlock(
                    onHistoryClick = onHistoryClick,
                    onContactsClick = onContactsClick,
                    onAboutClick = onAboutClick
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { viewModel.onIntent(ProfileIntents.OnEditRequested) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF44464E))
                ) {
                    Text("Редактировать профиль", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6542))
                ) {
                    Text("Выйти из аккаунта", color = Color.White)
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (state.isEditing) {
            ModalBottomSheet(
                containerColor = Color(0xFF272933),
                onDismissRequest = {
                    viewModel.onIntent(ProfileIntents.OnEditDismissed)
                },
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                EditProfileContent(
                    initialName = user.name.orEmpty(),
                    initialEmail = user.email.orEmpty(),
                    onSave = { name, email ->
                        viewModel.onIntent(ProfileIntents.OnEditSubmitted(name, email))
                    },
                    onCancel = {
                        viewModel.onIntent(ProfileIntents.OnEditDismissed)
                    }
                )
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onIntent(ProfileIntents.OnLogoutClicked)
                            showLogoutDialog = false
                        }
                    ) {
                        Text("Да", color = Color(0xFFFF9800))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Отмена", color = Color(0xFFFF9800))
                    }
                },
                title = { Text("Выход", color = Color.White) },
                text = { Text("Вы точно хотите выйти из аккаунта?", color = Color.White) },
                containerColor = Color(0xFF2C2F36),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}
