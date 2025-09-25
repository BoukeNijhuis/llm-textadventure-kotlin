package nl.boukenijhuis.provider

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.mistralai.MistralAiChatModel

class Mistral(model: String) : AbstractProvider(model) {
    override val chatModel: ChatModel
        get() = MistralAiChatModel.builder()
            .apiKey(System.getenv("MISTRAL_AI_API_KEY"))
            .modelName(model) // prevents rate limiter logging
            .maxRetries(1)
            .build()

    override val defaultModel: String
        get() = "mistral-small-latest"

    override val rateLimitMessage: String = "java.lang.RuntimeException: status code: 429"

}
