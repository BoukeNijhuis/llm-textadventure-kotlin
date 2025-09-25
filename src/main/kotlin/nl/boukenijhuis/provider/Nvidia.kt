package nl.boukenijhuis.provider

class Nvidia(model: String?) : AbstractOpenAiProvider(model) {
    override fun getEnvironmentVariableValue(): String {
        return "NVIDIA_API_KEY"
    }

    override fun getBaseURL(): String {
        return "https://integrate.api.nvidia.com/v1"
    }

    override val defaultModel: String
        get() = "meta/llama-3.3-70b-instruct"

    override fun getRateLimitMessage(): String {
        return "java.lang.RuntimeException: status code: 429"
    }
}
