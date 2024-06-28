package com.app.camchat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.camchat.composables.ChatUiModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val messagesCollection = firestore.collection("messages")

    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message.initConv)
    )

    val conversation: StateFlow<List<ChatUiModel.Message>>
        get() = _conversation

    init {
        loadMessages()
    }

    private fun loadMessages() {
        messagesCollection.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                return@addSnapshotListener
            }

            val messages = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ChatUiModel.Message::class.java)
            }

            viewModelScope.launch {
                _conversation.emit(messages)
            }
        }
    }

    fun sendChat(msg: String) {
        val myChat = ChatUiModel.Message(text = msg, author = ChatUiModel.Author.me)
        viewModelScope.launch {
            messagesCollection.add(myChat)
        }
    }

    fun addBotMessage(imageUrl: String) {
        val botMessage = ChatUiModel.Message(imageUrl = imageUrl, author = ChatUiModel.Author.bot)
        viewModelScope.launch {
            messagesCollection.add(botMessage)
        }
    }
}