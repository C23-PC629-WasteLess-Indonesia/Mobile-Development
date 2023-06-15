package com.example.wasteless.ui.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.wasteless.utils.Utils.isValidEmail

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    isError: (Boolean) -> Unit,
){
    var emailText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isError by remember {
        mutableStateOf(false)
    }


    OutlinedTextField(
        value = emailText,
        onValueChange = {
            emailText = it
            isError = !it.text.isValidEmail()
            onTextChange(it.text)
            isError(isError)
        },
        label = { Text(text = stringResource(id = com.example.wasteless.R.string.email)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
        singleLine = true,
        isError = isError,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary
        ),
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = "youremail@mail.com") }
    )
}

@Preview(showBackground = true)
@Composable
private fun EmailTextFieldPreview(){
    EmailTextField(onTextChange = {

    }, isError = {

    })
}