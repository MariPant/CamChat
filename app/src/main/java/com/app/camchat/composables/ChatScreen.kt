package com.app.camchat.composables


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.app.camchat.R
import com.example.compose.CamChatTheme

@Composable
fun ChatScreen(
    model: ChatUiModel,
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (messages, chatBox) = createRefs()

        val listState = rememberLazyListState()
        LaunchedEffect(model.messages.size) {
            listState.animateScrollToItem(model.messages.size)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messages) {
                    top.linkTo(parent.top)
                    bottom.linkTo(chatBox.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(16.dp)
        ) {
            items(model.messages) { item ->
                ChatItem(item)
            }
        }

        ChatBox(
            onSendChatClickListener = onSendChatClickListener,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(chatBox) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    CamChatTheme {
        ChatScreen(
            model = ChatUiModel(
                messages = listOf(
                    ChatUiModel.Message(
                        "Person Detected!",
                        author = ChatUiModel.Author("0", "Bot")
                    ),
                    ChatUiModel.Message(
                        drawableRes = R.drawable.person,  // Add your drawable image
                        author = ChatUiModel.Author("0", "Bot")
                    ),
                    ChatUiModel.Message(
                        "Goot job!",
                        author = ChatUiModel.Author(MY_ID, "Me")
                    )
                ),
                addressee = ChatUiModel.Author("0", "Bot")
            ),
            onSendChatClickListener = {},
            modifier = Modifier
        )
    }
}