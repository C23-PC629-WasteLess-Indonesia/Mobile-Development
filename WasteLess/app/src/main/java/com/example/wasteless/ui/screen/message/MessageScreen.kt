package com.example.wasteless.ui.screen.message

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wasteless.model.dummymodel.Conversation
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.theme.SoftGray
import com.google.firebase.database.FirebaseDatabase
import java.util.*

@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userPreferences: UserPreferences,
    navigateToConversation: (Int) -> Unit
){
    userPreferences.getUser().userId?.let { viewModel.getMessage(it.toInt()) }
    val messages = viewModel.messages.value.distinctBy { it.messageId }

    Text(
        text = "Messages",
        style = MaterialTheme.typography.subtitle1.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = modifier.padding(start = 16.dp, top = 16.dp)
    )
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, top = 70.dp, end = 16.dp)
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
                        text = "${message.namaReceiver}",
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
        item { 
            Spacer(modifier = modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    messageId: Int,
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userPreferences: UserPreferences,
    onBackClick: () -> Unit,
){
    viewModel.getConversation(messageId.toString())
    val conversations = viewModel.conversations.value.sortedBy { it.time }
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondary),
                contentAlignment = Alignment.CenterStart
            ){
                Row(
                    modifier = modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = modifier.clickable {
                            onBackClick()
                        },
                        tint = Color.White
                    )
                    Text(
                        text = "Penawaran",
                        color = Color.White,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        modifier = modifier.padding(start = 10.dp)
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = modifier
                    .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var message by remember {
                    mutableStateOf(TextFieldValue())
                }
                var buttonClicked by remember {
                    mutableStateOf(false)
                }
                TextField(
                    value = message,
                    onValueChange = {
                        message = it
                    },
                    modifier = modifier.weight(2f),
                    placeholder = {
                        if (!buttonClicked){
                            Text(text = "Ketik pesan")
                        }
                    }
                )
                Spacer(modifier = modifier.padding(start = 4.dp))
                Button(onClick = {
                    val conversation = Conversation(
                        messageId.toString(),
                        userPreferences.getUser().userId?.toInt(),
                        message.text.toString(),
                        Date().time
                    )
                    val database = FirebaseDatabase.getInstance().getReference("Conversation")
                    database.push().setValue(conversation)
                    buttonClicked = true
                    message = TextFieldValue()
                }) {
                    Text(
                        text = "Kirim",
                        modifier = modifier.padding(vertical = 10.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            LazyColumn(modifier = modifier.fillMaxWidth()){
                items(conversations){ conversation ->
                    if (conversation.senderId == userPreferences.getUser().userId?.toInt()){
                        Box(modifier = modifier
                            .padding(start = 100.dp, end = 16.dp, top = 8.dp)
                            .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Box(
                                modifier = modifier
                                    .clip(RoundedCornerShape(6.dp, 6.dp, 0.dp, 6.dp))
                                    .background(
                                        Color(SoftGray.hashCode())
                                    )
                            ) {
                                conversation.message?.let { Text(
                                    text = it,
                                    modifier = modifier.padding(8.dp)
                                ) }
                            }
                        }
                    }else{
                        Box(modifier = modifier
                            .padding(start = 16.dp, end = 100.dp, top = 16.dp)
                            .fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ){
                            Box(
                                modifier = modifier
                                    .clip(RoundedCornerShape(6.dp, 6.dp, 6.dp, 0.dp))
                                    .background(
                                        Color(SoftGray.hashCode())
                                    )
                            ) {
                                conversation.message?.let { Text(
                                    text = it,
                                    modifier = modifier.padding(8.dp)
                                ) }
                            }
                        }
                    }
                }
            }
        }
    }
}