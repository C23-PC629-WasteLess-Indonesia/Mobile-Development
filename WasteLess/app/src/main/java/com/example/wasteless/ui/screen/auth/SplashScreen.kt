package com.example.wasteless.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToMain: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "WasteLess",
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.White
        )
    }

    LaunchedEffect(Unit){
        delay(3000)
        navigateToMain()
    }
}