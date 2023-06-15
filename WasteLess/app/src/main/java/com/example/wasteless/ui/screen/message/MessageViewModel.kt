package com.example.wasteless.ui.screen.message


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wasteless.model.dummymodel.Conversation
import com.example.wasteless.model.dummymodel.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.StateFlow

class MessageViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance()

    private var _messages = mutableStateOf<List<Message>>(emptyList())
    val messages: State<List<Message>> = _messages

    private var _messagesReceiver = mutableStateOf<List<Message>>(emptyList())
    val messagesReceiver: State<List<Message>> = _messagesReceiver

    private var _conversation = mutableStateOf<List<Conversation>>(emptyList())
    val conversations: State<List<Conversation>> = _conversation


    fun getMessage(senderId: Int) {
        database.getReference("Messages").orderByChild("senderId").equalTo(senderId.toDouble())
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (childSnapshot in snapshot.children) {
                    val message = childSnapshot.getValue(Message::class.java)
                    message?.let {
                        messages.add(it)
                    }
                }
                _messages.value = messages
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancellation error if needed
            }
        })
    }
    fun getMessageReceiver(receiverId: Int){
        database.getReference("Messages").orderByChild("receiverId").equalTo(receiverId.toDouble())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<Message>()
                    for (childSnapshot in snapshot.children){
                        val message = childSnapshot.getValue(Message::class.java)
                        message?.let {
                            messages.add(it)
                        }
                    }
                    _messagesReceiver.value = messages
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
    fun getConversation(messageId: String){
        database.getReference("Conversation").orderByChild("messageId").equalTo(messageId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val conversations = mutableListOf<Conversation>()
                    for (childSnapshot in snapshot.children){
                        val conversation = childSnapshot.getValue(Conversation::class.java)
                        conversation?.let {
                            conversations.add(it)
                        }
                    }
                    _conversation.value = conversations
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}