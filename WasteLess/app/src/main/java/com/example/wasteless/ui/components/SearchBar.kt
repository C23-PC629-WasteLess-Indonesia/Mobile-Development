package com.example.wasteless.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.wasteless.ui.theme.SoftGray

@Composable
fun SearchBar(
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var query by remember{
        mutableStateOf(TextFieldValue(""))
    }
    androidx.compose.material.TextField(
        value = query,
        onValueChange = {
            query = it
            onQueryChange(it.text)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(SoftGray.hashCode()),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(text = "find your favorite food")
        },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
    )
}