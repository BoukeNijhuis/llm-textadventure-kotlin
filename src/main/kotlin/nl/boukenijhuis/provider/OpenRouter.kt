package nl.boukenijhuis.provider

class OpenRouter(model: String?) : AbstractOpenAiProvider(model) {
    override fun getEnvironmentVariableValue(): String {
        return "OPENROUTER_API_KEY"
    }

    override fun getBaseURL(): String {
        return "https://openrouter.ai/api/v1"
    }

    override val defaultModel: String
        get() = "deepseek/deepseek-chat-v3.1:free"

    override fun getRateLimitMessage(): String {
        return "Rate limit exceeded"
    }
}
