package com.example.wasteless

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.navigation.Screen
import com.example.wasteless.ui.theme.WasteLessTheme
import com.google.android.gms.maps.MapsInitializer
import com.google.android.libraries.places.api.Places
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var userPreferences: UserPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WasteLessTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    val userPreferences = UserPreferences(LocalContext.current)

                    WasteLessApp(navController = navController, userPreferences = userPreferences)

                }
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentBackStackEntry?.destination?.route == Screen.Home.route){
            val userPreferences = UserPreferences(this)
            userPreferences.removeUser()
            finish()
        }else{
            super.onBackPressed()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WasteLessTheme {
        Greeting("Android")
    }
}