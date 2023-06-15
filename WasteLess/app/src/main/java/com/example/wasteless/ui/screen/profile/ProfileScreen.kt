package com.example.wasteless.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wasteless.ViewModelfactory
import com.example.wasteless.di.Injection
import com.example.wasteless.model.response.UserProfileResponse
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.common.UiState
import com.example.wasteless.ui.screen.home.HomeViewModel
import com.example.wasteless.ui.screen.message.MessageViewModel
import com.example.wasteless.ui.screen.viewmodel.UserViewModel
import com.example.wasteless.ui.theme.SoftGray

@Composable
fun ProfileScreen(
    userPreferences: UserPreferences,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelfactory(Injection.provideFoodRepository(), Injection.provideUserRepository())
    ),
    navigateToConversation: (Int) -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getUserData(userPreferences.getUser().token.toString())
            }
            is UiState.Success -> {
                ProfileContent(userData = uiState.data, userPreferences = userPreferences, navigateToConversation = navigateToConversation)
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userData: UserProfileResponse,
    userPreferences: UserPreferences,
    navigateToConversation: (Int) -> Unit,
){
    viewModel.getFoodByUserId(userPreferences.getUser().token.toString(), userData.userId)
    val datahistory by remember {
        viewModel.historyFood
    }.collectAsState()
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = modifier.padding(top = 24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.AccountCircle, 
                contentDescription = null,
                modifier = modifier.size(60.dp)
            )
            Column {
                Text(
                    text = userData.name,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp
                    )
                )
            }
        }
        Spacer(modifier = modifier.padding(top = 24.dp))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
                .padding(top = 30.dp, bottom = 30.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (datahistory.isNotEmpty()){
                    Text(
                        text = datahistory.size.toString(),
                        color = Color.White
                    )
                }else{
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = Color.White
                    )
                }
                Text(
                    text = "Makanan telah kamu bagikan",
                    color = Color.White
                )
            }
        }
        Spacer(modifier = modifier.padding(top = 16.dp))
        Text(
            text = "Chat dari peminat",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        ChatFromPeminat(userPreferences = userPreferences, navigateToConversation = navigateToConversation )
    }
}
@Composable
private fun ChatFromPeminat(
    modifier: Modifier = Modifier,
    userPreferences: UserPreferences,
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigateToConversation: (Int) -> Unit
){
    userPreferences.getUser().userId?.let { viewModel.getMessageReceiver(it.toInt()) }

    val messages = viewModel.messagesReceiver.value.distinctBy { it.messageId }

    if (messages.isEmpty()){
        Column(
            modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Belum ada peminat")
        }
    }else{
        LazyColumn(
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ){
            items(messages){ message ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(Color(SoftGray.hashCode()))
                        .padding(top = 4.dp, start = 2.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                            .drawWithContent {
                                drawContent()
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(0F, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                            .clickable {
                                message.messageId?.let { navigateToConversation(it.toInt()) }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = modifier.size(50.dp)
                        )
                        Spacer(modifier = modifier.padding(start = 8.dp))
                        Text(
                            text = "${message.namaSender}",
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
