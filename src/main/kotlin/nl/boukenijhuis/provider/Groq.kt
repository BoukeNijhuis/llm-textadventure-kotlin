package nl.boukenijhuis.provider

class Groq(model: String?) : AbstractOpenAiProvider(model) {
    override fun getEnvironmentVariableValue(): String {
        return "GROQ_API_KEY"
    }

    override fun getBaseURL(): String {
        return "https://api.groq.com/openai/v1"
    }

    override val defaultModel: String
        get() = "llama-3.3-70b-versatile"

    override fun getRateLimitMessage(): String {
        return "Rate limit reached"
    }
}
