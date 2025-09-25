package nl.boukenijhuis.provider

class Together(model: String) : AbstractOpenAiProvider(model) {
    override val environmentVariableValue: String = "TOGETHER_API_KEY"

    override val baseURL: String = "https://api.together.xyz/v1"

    override val defaultModel: String
        get() = "openai/gpt-oss-20b"

    override val rateLimitMessage: String = ""
}
