package com.app.camchat.composables

data class ChatUiModel(
    val messages: List<Message>,
    val addressee: Author
) {
    data class Message(
        val text: String? = null,
        val imageUrl: String? = null,
        val author: Author? = null
    ) {
        val isFromMe: Boolean
            get() = author?.id == MY_ID

        companion object {
            val initConv = Message(
                text = "Face Recognition Alert System Active.",
                author = Author("0", "Bot")
            )
        }
    }

    data class Author(val id: String = "", val name: String = "") {
        companion object {
            val me = Author(MY_ID, "Me")
            val bot = Author("0", "Bot")
        }
    }
}

const val MY_ID = "-1"