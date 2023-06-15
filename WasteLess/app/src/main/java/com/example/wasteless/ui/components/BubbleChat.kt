package com.example.wasteless.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wasteless.model.dummymodel.Message

@Composable
fun BubbleChat(
    modifier: Modifier = Modifier,
    message: Message
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle, 
            contentDescription = null,
            modifier = modifier.size(45.dp)
        )
        Spacer(modifier = modifier.padding(start = 12.dp))

    }
}

