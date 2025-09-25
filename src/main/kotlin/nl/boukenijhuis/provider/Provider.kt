package nl.boukenijhuis.provider

import dev.langchain4j.model.chat.ChatModel

interface Provider {
    val chatModel: ChatModel

    val defaultModel: String

    @Throws(Exception::class)
    fun handleException(e: Exception?): String?

    val model: String
}
