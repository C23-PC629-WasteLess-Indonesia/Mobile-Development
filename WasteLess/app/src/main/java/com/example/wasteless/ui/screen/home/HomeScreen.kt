package com.example.wasteless.ui.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.media.session.MediaSession.Token
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wasteless.R
import com.example.wasteless.ViewModelfactory
import com.example.wasteless.di.Injection
import com.example.wasteless.model.dummymodel.Food
import com.example.wasteless.model.dummymodel.User
import com.example.wasteless.model.response.UserProfileResponse
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.common.UiState
import com.example.wasteless.ui.components.FoodItem
import com.example.wasteless.ui.components.FoodItemHome
import com.example.wasteless.ui.components.SearchBar
import com.example.wasteless.ui.screen.viewmodel.UserViewModel
import com.example.wasteless.ui.theme.TextGray
import com.example.wasteless.utils.LatAndLong
import com.google.android.gms.location.LocationServices
import java.util.*

@Composable
fun HomeScreen(
    userPreferences: UserPreferences,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelfactory(Injection.provideFoodRepository(), Injection.provideUserRepository())
    ),
    navigateToDetail: (Int) -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getUserData(userPreferences.getUser().token.toString())
            }
            is UiState.Success -> {
                HomeContent(userPreferences = userPreferences, navigateToDetail = navigateToDetail, userData = uiState.data)
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
private fun HomeContent(
modifier: Modifier = Modifier,
userPreferences: UserPreferences,
navigateToDetail: (Int) -> Unit,
userData: UserProfileResponse,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = modifier.padding(top = 16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column {
                Text(
                    text = "Selamat datang ${userData.name}",
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    ),
                    color = Color(TextGray.hashCode())
                )
                Text(
                    text = "Ambil dan nikmati makanan disekitarmu!",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        Spacer(modifier = modifier.padding(top = 16.dp))
        Text(
            text = stringResource(id = R.string.recomended),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        RecomendationFood(navigateToDetail = navigateToDetail, token = userPreferences.getUser().token.toString())
        Text(
            text = stringResource(id = R.string.makananGratis),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        )
        Food(navigateToDetail = navigateToDetail, token = userPreferences.getUser().token.toString())

    }
}
@Composable
private fun RecomendationFood(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelfactory(Injection.provideFoodRepository(), Injection.provideUserRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    token: String,
){
    viewModel.getFoodsPreference(token)
    val data by remember {
        viewModel.foods
    }.collectAsState()

    LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
        item {
            Spacer(modifier = modifier.padding(start = 16.dp))
        }
        items(data){ food ->
            FoodItem(
                image = food.fotoMakanan,
                nama = food.foodName,
                lokasi = food.location,
                modifier = modifier.clickable {
                    navigateToDetail(food.foodId)
                }
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Food(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelfactory(Injection.provideFoodRepository(), Injection.provideUserRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    token: String
){
    viewModel.getAllFoods(token)
    val data by remember {
        viewModel.allFoods
    }.collectAsState()

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        for (food in data){
            FoodItemHome(
                image = food.fotoMakanan,
                nama = food.foodName,
                lokasi = food.location,
                modifier = modifier.clickable {
                    navigateToDetail(food.foodId)
                }
            )
            Spacer(modifier = modifier.padding(top = 4.dp))
        }
    }
}