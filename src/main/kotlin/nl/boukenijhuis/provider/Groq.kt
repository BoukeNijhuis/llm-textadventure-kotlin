package nl.boukenijhuis.provider

class Groq(model: String) : AbstractOpenAiProvider(model) {
    override val environmentVariableValue: String = "GROQ_API_KEY"

    override val baseURL: String = "https://api.groq.com/openai/v1"

    override val defaultModel: String
        get() = "llama-3.3-70b-versatile"

    override val rateLimitMessage: String ="Rate limit reached"

}
