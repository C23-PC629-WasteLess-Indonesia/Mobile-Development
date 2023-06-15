package com.example.wasteless.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.wasteless.R
import com.example.wasteless.utils.Utils.isValidEmail

@Composable
fun CustomtextField(
    label: Int,
    placeholder: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
){
    var emailText by remember {
        mutableStateOf(TextFieldValue(""))
    }


    OutlinedTextField(
        value = emailText,
        onValueChange = {
            emailText = it
            onTextChange(it.text)
        },
        label = { Text(text = stringResource(id = label)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary
        ),
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = placeholder) }
    )
}