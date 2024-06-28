package com.app.camchat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight


@Composable
fun SentMessageItem(message: ChatUiModel.Message) {
    Box(
        contentAlignment = Alignment.BottomEnd, // Align content to the end (right side)
        modifier = Modifier
            .wrapContentWidth()
            .padding(vertical = 4.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(primaryContainerLight) // Change color as needed
            .padding(16.dp)
    ) {
        Text(
            text = message.text ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black // Text color for user messages
        )
    }
}

@Composable
fun BotMessageItem(message: ChatUiModel.Message) {
    Box(
        contentAlignment = Alignment.TopStart, // Align content to the start (left side)
        modifier = Modifier
            .wrapContentWidth()
            .padding(vertical = 4.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(primaryLight) // Change color as needed
            .padding(16.dp)

    ) {
        Text(
            text = message.text ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White // Text color for bot messages
        )
    }
}

