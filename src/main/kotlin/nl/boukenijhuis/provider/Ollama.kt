package nl.boukenijhuis.provider

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.ollama.OllamaChatModel

class Ollama(model: String) : AbstractProvider(model) {
    override val chatModel: ChatModel = OllamaChatModel.builder()
            .modelName(this.model)
            .baseUrl("http://localhost:11434")
            .maxRetries(0)
            .build()

    override val defaultModel: String
        get() = "gemma3:12b"

    override val rateLimitMessage: String = ""
}
