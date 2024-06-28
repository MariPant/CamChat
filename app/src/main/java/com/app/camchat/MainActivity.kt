package com.app.camchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.app.camchat.composables.ChatScreen
import com.app.camchat.composables.ChatUiModel
import com.app.camchat.viewModel.MainViewModel
import com.example.compose.CamChatTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase
        Firebase.initialize(this)
        setContent {
            val conversation = viewModel.conversation.collectAsState()

            CamChatTheme {
                Surface {
                    ChatScreen(
                        model = ChatUiModel(
                            messages = conversation.value,
                            addressee = ChatUiModel.Author("0", "Bot")
                        ),
                        onSendChatClickListener = { msg -> viewModel.sendChat(msg) },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}