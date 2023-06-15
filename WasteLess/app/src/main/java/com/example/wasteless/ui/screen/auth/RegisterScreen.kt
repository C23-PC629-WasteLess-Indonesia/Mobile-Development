package com.example.wasteless.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wasteless.R
import com.example.wasteless.ui.common.UiState
import com.example.wasteless.ui.components.AutocompleteTextField
import com.example.wasteless.ui.components.ButtonCustom
import com.example.wasteless.ui.components.CustomtextField
import com.google.android.gms.maps.MapsInitializer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val context = LocalContext.current

    val error: Boolean by viewModel.error.collectAsState(initial = false)
    var nama by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }


    Column(
        modifier = modifier
            .padding(top = 45.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logowasteless),
            contentDescription = "Logo wasteless",
            modifier = modifier.size(200.dp, 30.dp)
        )
        Text(
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
        Spacer(modifier = modifier.padding(32.dp))
        CustomtextField(
            label = R.string.nama,
            placeholder = "nama lengkap",
            onTextChange = {
                nama = it
            }
        )
        Spacer(modifier = modifier.padding(4.dp))
        CustomtextField(
            label = R.string.email,
            placeholder = "youremail@mail.com",
            onTextChange = {
                email = it
            }
        )
        Spacer(modifier = modifier.padding(4.dp))
        CustomtextField(
            label = R.string.password,
            placeholder = password,
            onTextChange = {
                password = it
            }
        )
        Spacer(modifier = modifier.padding(24.dp))
        ButtonCustom(
            text = "Register",
            color = MaterialTheme.colors.secondary
        ) {
            viewModel.registerUser(nama, email, password)
            if (error){
                Toast.makeText(context, "Register tidak berhasil", Toast.LENGTH_SHORT).show()
            }else{
                navigateToLogin()
            }
        }
        Spacer(modifier = modifier.padding(70.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sudah punya akun?")
            Text(
                text = "Login",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier.clickable {
                    navigateToLogin()
                }
            )
        }
    }
}