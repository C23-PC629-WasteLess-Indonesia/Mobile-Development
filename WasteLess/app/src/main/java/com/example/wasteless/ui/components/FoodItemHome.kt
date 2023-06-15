package com.example.wasteless.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wasteless.ui.theme.SoftGray

@Composable
fun FoodItemHome(
    image: String,
    nama: String,
    lokasi: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Color(SoftGray.hashCode()))
    ) {
        val imagePainter = rememberAsyncImagePainter(image)
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = modifier.padding(8.dp))
        Column(
            modifier = modifier.padding(top = 16.dp)
        ) {
            Text(
                text = nama,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            )
            Text(
                text = lokasi,
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}