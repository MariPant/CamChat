package com.app.camchat.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.compose.primaryContainerLight

@Composable
fun ChatItem(message: ChatUiModel.Message) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        val chatBubbleId = if (message.isFromMe) Arrangement.End else Arrangement.Start
        Box(
            modifier = Modifier
                .layoutId(chatBubbleId)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .background(primaryContainerLight)
                .padding(16.dp)
        ){
            when {
                message.drawableRes != null -> {
                    Image(
                        painter = painterResource(id = message.drawableRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                message.imageUrl != null -> {
                    // If you still want to handle URLs (optional)
                    Image(
                        painter = rememberImagePainter(data = message.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                message.text != null -> {
                    Text(text = message.text)
                }
            }
        }
    }
    }