package com.example.wasteless.ui.screen.detail

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wasteless.R
import com.example.wasteless.ViewModelfactory
import com.example.wasteless.di.Injection
import com.example.wasteless.model.dummymodel.*
import com.example.wasteless.model.response.FoodDetailResponse
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.common.UiState
import com.example.wasteless.ui.screen.viewmodel.UserViewModel
import com.example.wasteless.ui.theme.SoftGreen
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.maps.android.compose.*
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    foodId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userPreferences: UserPreferences,
    navigateToConversationScreen: (Int) -> Unit,
){
    viewModel.getFoodById(userPreferences.getUser().token.toString(), foodId)

    val data = viewModel.detailFood.collectAsState().value
    val responseHistory = viewModel.history.collectAsState().value
    if (data != null) {
        DetailContent(
            food = data,
            onBackClick = navigateBack,
            userPreferences = userPreferences,
            navigateToConversationScreen = navigateToConversationScreen,
            responseHistory = responseHistory,
            token = userPreferences.getUser().token.toString(),
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    food: FoodDetailResponse,
    onBackClick: () -> Unit,
    userPreferences: UserPreferences,
    navigateToConversationScreen: (Int) -> Unit,
    responseHistory: String,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModel2: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    token: String
){
    var namaUser by remember {
        mutableStateOf("")
    }
    viewModel2.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel2.getUserData(token)
            }
            is UiState.Success -> {
                namaUser = uiState.data.name
            }
            is UiState.Error -> {

            }
        }
    }
    val context = LocalContext.current
    Box(
        modifier = modifier.fillMaxWidth(),
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = modifier){
                Image(
                    painter = rememberAsyncImagePainter(model = food.fotoMakanan),
                    contentDescription = food.foodName,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
                Column(modifier = modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = modifier
                            .size(30.dp)
                            .clickable {
                                onBackClick()
                            },
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = food.foodName,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_outline_calendar_month_24),
                            contentDescription = null,
                            modifier = modifier.size(16.dp)
                        )
                        Text(
                            text = "Ambil sebelum : ${food.expiredAt}",
                            modifier = modifier.padding(start = 4.dp),
                            style = MaterialTheme.typography.caption.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
                Row(
                    modifier = modifier.padding(top = 16.dp)
                ) {
                    Image(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "user profile",
                        modifier = modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = modifier.padding(start = 4.dp)
                    ) {
                        Text(
                            text = food.name,
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        )
                        Text(
                            text = food.location,
                            style = MaterialTheme.typography.caption.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
                Text(
                    text = "Deskripsi",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    modifier = modifier.padding(top = 16.dp)
                )
                Text(
                    text = food.description,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    modifier = modifier.padding(top = 1.dp)
                )
                Text(
                    text = "Lokasi",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    modifier = modifier.padding(top = 16.dp)
                )
                Text(
                    text = food.location,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    modifier = modifier.padding(top = 1.dp, bottom = 1.dp)
                )
                MapViewFood(latitude = food.latitude.toDouble(), longitude = food.longitude.toDouble())
                Spacer(modifier = modifier.padding(25.dp))
            }

        }
        if ((userPreferences.getUser().userId?.toInt() ?: 0) != food.userId){
            Button(
                onClick = {
                    val message = Message(
                        "${userPreferences.getUser().userId}${food.userId}",
                        food.foodId,
                        userPreferences.getUser().userId?.toInt(),
                        namaUser,
                        food.userId,
                        namaReceiver = food.name
                    )
                    val conversation = Conversation(
                        "${userPreferences.getUser().userId}${food.userId}",
                        userPreferences.getUser().userId?.toInt(),
                        "Hai saya tertarik dengan ${food.foodName}, apakah saya boleh mengambilnya?",
                        Date().time
                    )

                    val database1 = FirebaseDatabase.getInstance().getReference("Messages")
                    database1.push().setValue(message)
                    val database2 = FirebaseDatabase.getInstance().getReference("Conversation")
                    database2.push().setValue(conversation)

                    userPreferences.getUser().userId?.let { viewModel.createHistory(token = token, it.toInt(), food.foodId, food.userId, false) }
                    Toast.makeText(context,responseHistory, Toast.LENGTH_SHORT ).show()

                    navigateToConversationScreen("${userPreferences.getUser().userId}${food.userId}".toInt())
                },
                modifier = modifier
                    .offset(16.dp, 630.dp)
                    .width(350.dp)
            ) {
                Text(text = "Tertarik dan hubungi donatur")
            }
        }

    }
}

@Composable
private fun MapViewFood(
    modifier: Modifier = Modifier,
    latitude: Double,
    longitude: Double,
){
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 12f)
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(compassEnabled = true),
        modifier = modifier.height(200.dp)
    ){
        Marker(
            state = rememberMarkerState(position = LatLng(latitude, longitude)),
            icon = BitmapDescriptorFactory.defaultMarker()
        )
    }
}

