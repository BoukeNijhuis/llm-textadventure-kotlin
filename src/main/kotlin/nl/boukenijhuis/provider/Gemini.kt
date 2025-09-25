package nl.boukenijhuis.provider

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel

class Gemini(model: String?) : AbstractProvider(model) {
    override val chatModel: ChatModel
        get() = GoogleAiGeminiChatModel.builder()
            .apiKey(System.getenv("GEMINI_API_KEY"))
            .modelName(model) // prevents rate limiter logging
            .maxRetries(0)
            .build()

    override val defaultModel: String
        get() = "gemini-2.5-flash-lite"

    override fun getRateLimitMessage(): String {
        return "type.googleapis.com/google.rpc.QuotaFailure"
    }
}
