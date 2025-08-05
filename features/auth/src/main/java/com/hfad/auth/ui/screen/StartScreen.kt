package com.hfad.auth.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.auth.R
import com.hfad.auth.ui.font.InterFontFamily
import kotlinx.coroutines.delay

@Composable
fun Modifier.clickableScale(): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "ClickScale"
    )
    return this.scale(scale)
}

@Composable
fun StartScreen(
    onStart: () -> Unit,
    onSkipClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "Alpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.95f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "Scale"
    )

    val buttonAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800, delayMillis = 200),
        label = "ButtonAlpha"
    )

    val showLoading = false // 👈 поменяй на true для демонстрации прогресса

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2129))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .alpha(alpha)
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_tm),
                contentDescription = "Логотип",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Text(
            text = "#Территория\nмяча",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Black,
            fontFamily = InterFontFamily,
            textAlign = TextAlign.Center,
            lineHeight = 52.sp,
            modifier = Modifier
                .alpha(alpha)
                .scale(scale)
                .padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .alpha(buttonAlpha)
                .clickableScale(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6542))
        ) {
            Text(
                "Войти",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onSkipClick,
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
                .alpha(buttonAlpha)
                .clickableScale(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF272933)
            )
        ) {
            Text(
                "Пропустить",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )
        }

        if (showLoading) {
            CircularProgressIndicator(
                color = Color(0xFFFF9800),
                trackColor = Color(0x33FFFFFF),
                strokeWidth = 6.dp,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .size(36.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen(onStart = {}, onSkipClick = {})
}
