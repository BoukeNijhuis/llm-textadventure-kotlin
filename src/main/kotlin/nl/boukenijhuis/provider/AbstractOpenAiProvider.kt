package nl.boukenijhuis.provider

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.openai.OpenAiChatModel

abstract class AbstractOpenAiProvider(model: String) : AbstractProvider(model) {
    override val chatModel: ChatModel
        get() = OpenAiChatModel.builder()
            .apiKey(System.getenv(this.environmentVariableValue))
            .baseUrl(this.baseURL)
            .modelName(model)
            // prevents rate limiter logging
            .maxRetries(0)
            .build()

    abstract val environmentVariableValue: String
    abstract val baseURL: String
}
