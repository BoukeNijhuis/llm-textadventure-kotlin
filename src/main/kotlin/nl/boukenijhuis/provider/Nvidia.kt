package nl.boukenijhuis.provider

class Nvidia(model: String) : AbstractOpenAiProvider(model) {
    override val environmentVariableValue: String = "NVIDIA_API_KEY"

    override val baseURL: String = "https://integrate.api.nvidia.com/v1"

    override val defaultModel: String
        get() = "meta/llama-3.3-70b-instruct"

    override val rateLimitMessage: String = "java.lang.RuntimeException: status code: 429"

}
