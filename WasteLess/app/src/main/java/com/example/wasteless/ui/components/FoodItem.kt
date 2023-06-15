package com.example.wasteless.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun FoodItem(
    image: String,
    nama: String,
    lokasi: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.size(width= 180.dp, height = 275.dp)
    ) {
        val imagePainter = rememberAsyncImagePainter(image)
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(180.dp, 230.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = nama,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = modifier.padding(top = 6.dp)
        )
        Text(
            text = lokasi,
            style = MaterialTheme.typography.caption.copy(
                fontSize = 12.sp,
            )
        )
    }
}