package nl.boukenijhuis.provider

class Together(model: String?) : AbstractOpenAiProvider(model) {
    override fun getEnvironmentVariableValue(): String {
        return "TOGETHER_API_KEY"
    }

    override fun getBaseURL(): String {
        return "https://api.together.xyz/v1"
    }

    override val defaultModel: String
        get() = "openai/gpt-oss-20b"

    override fun getRateLimitMessage(): String {
        // dummy implementation
        return ""
    }
}
