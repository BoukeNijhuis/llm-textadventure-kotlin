package nl.boukenijhuis.provider

class OpenRouter(model: String) : AbstractOpenAiProvider(model) {
    override val environmentVariableValue: String = "OPENROUTER_API_KEY"

    override val baseURL: String = "https://openrouter.ai/api/v1"

    override val defaultModel: String
        get() = "deepseek/deepseek-chat-v3.1:free"

    override val rateLimitMessage: String = "Rate limit exceeded"
}
