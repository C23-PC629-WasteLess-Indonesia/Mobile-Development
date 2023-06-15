package com.example.wasteless.ui.screen.auth

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.wasteless.model.dummymodel.UserPref
import com.example.wasteless.model.response.LoginResponse
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.components.ButtonCustom
import com.example.wasteless.ui.components.CustomtextField
import com.example.wasteless.ui.components.EmailTextField


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    userPreferences: UserPreferences,
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    Column(
        modifier = modifier
            .padding(top = 45.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logowasteless),
            contentDescription = "logo wasteless",
            modifier = modifier.size(200.dp,30.dp)
        )
        Text(
            text = stringResource(id = R.string.welcomeback),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
        Spacer(modifier = modifier.padding(32.dp))
        val context = LocalContext.current
        val error: Boolean by viewModel.error.collectAsState(initial = false)

        var email by remember {
            mutableStateOf("")
        }
        var isError by remember {
            mutableStateOf(false)
        }
        EmailTextField(
            onTextChange = {
                email = it
            }, isError = {
                isError = it
            }
        )
        Spacer(modifier = modifier.padding(4.dp))
        var password by remember {
            mutableStateOf("")
        }
        CustomtextField(label = R.string.password, placeholder = "password", onTextChange = {
            password = it
        })
        viewModel.loginUser(email, password)
        Spacer(modifier = modifier.padding(24.dp))
        ButtonCustom(text = "Login", color = MaterialTheme.colors.secondary, onClick = {
            val userAuth = viewModel.userAuth.value
            val user = UserPref()
            if (userAuth != null){
                user.isLogin = true
                user.token = userAuth.token
                user.userId = userAuth.userId.toString()
                user.lat =""
                user.lon = ""
                userPreferences.setUser(user)
                navigateToHome()
            }
        })
        Spacer(modifier = modifier.padding(100.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Belum punya akun?")
            Text(
                text = "Register",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier.clickable {
                    navigateToRegister()
                }
            )
        }
    }
}

