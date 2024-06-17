package com.app.camchat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.camchat.composables.ChatUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val questions = mutableListOf(
        "What about yesterday?",
        "Can you tell me what is inside your head?",
        "Lately, I've been wondering if I can really do anything, do you?",
        "You know fear is often just an illusion, have you ever experienced it?",
        "If you were me, what would you do?"
    )

    private val imageUrls = mutableListOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        "https://example.com/image3.jpg"
    )

    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message.initConv)
    )

    val conversation: StateFlow<List<ChatUiModel.Message>>
        get() = _conversation

    fun sendChat(msg: String) {
        val myChat = ChatUiModel.Message(text = msg, author = ChatUiModel.Author.me)
        viewModelScope.launch {
            _conversation.emit(_conversation.value + myChat)

            delay(1000)

            _conversation.emit(_conversation.value + getRandomBotResponse())
        }
    }

    private fun getRandomBotResponse(): ChatUiModel.Message {
        return if (Math.random() > 0.5 && imageUrls.isNotEmpty()) {
            val imageUrl = imageUrls.random()
            imageUrls.remove(imageUrl)
            ChatUiModel.Message(imageUrl = imageUrl, author = ChatUiModel.Author.bot)
        } else {
            val question = if (questions.isEmpty()) {
                "no further questions, please leave me alone"
            } else {
                questions.random()
            }

            if (questions.isNotEmpty()) questions.remove(question)

            ChatUiModel.Message(text = question, author = ChatUiModel.Author.bot)
        }
    }
}