package com.example.wasteless.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonCustom(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif
            ),
            color = MaterialTheme.colors.background
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonCustomPreview(){
    ButtonCustom(text = "Get started", color = MaterialTheme.colors.secondary) {

    }
}