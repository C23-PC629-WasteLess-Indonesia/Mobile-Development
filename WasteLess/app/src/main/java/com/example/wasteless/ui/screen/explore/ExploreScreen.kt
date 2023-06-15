package com.example.wasteless.ui.screen.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wasteless.model.dummymodel.FoodCategory
import com.example.wasteless.model.dummymodel.listCategory
import com.example.wasteless.model.response.AllFoodListResponseItem
import com.example.wasteless.model.response.FoodResponseItem
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.components.FoodItem
import com.example.wasteless.ui.components.SearchBar
import com.example.wasteless.ui.screen.home.HomeViewModel

@Composable
fun ExploreScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userPreferences: UserPreferences,
    navigateToDetail: (Int) -> Unit,
){
    viewModel.getAllFoods(userPreferences.getUser().token.toString())
    val data by remember {
        viewModel.allFoods
    }.collectAsState()

    ExploreContent(foods = data, categories = listCategory, navigateToDetail = navigateToDetail)

}

@Composable
private fun ExploreContent(
    modifier: Modifier = Modifier,
    foods: List<AllFoodListResponseItem>,
    categories: List<FoodCategory>,
    navigateToDetail: (Int) -> Unit,
){
    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Semua makanan",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = modifier.padding(top = 12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(foods){ food->
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

}
