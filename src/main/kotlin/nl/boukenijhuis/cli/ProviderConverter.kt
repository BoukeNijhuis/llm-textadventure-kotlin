package nl.boukenijhuis.cli

import nl.boukenijhuis.provider.*
import picocli.CommandLine.ITypeConverter
import java.util.*

class ProviderConverter : ITypeConverter<ProviderBuilder?> {
    enum class ValidProvider(val providerBuilder: ProviderBuilder) {
        OLLAMA(ProviderBuilder(Ollama::class.java)),
        GEMINI(ProviderBuilder(Gemini::class.java)),
        MISTRAL(ProviderBuilder(Mistral::class.java)),
        NVIDIA(ProviderBuilder(Nvidia::class.java)),
        GROQ(ProviderBuilder(Groq::class.java)),
        TOGETHER(ProviderBuilder(Together::class.java)),
        OPENROUTER(ProviderBuilder(OpenRouter::class.java));

        internal class CompletionCandidates : ArrayList<String?>(
            ArrayList<String?>(
                Arrays.stream<ValidProvider?>(entries.toTypedArray())
                    .map<String?> { obj: ValidProvider? -> obj!!.name }
                    .map<String?> { obj: String? -> obj!!.lowercase(Locale.getDefault()) }
                    .toList()))
    }

    override fun convert(value: String): ProviderBuilder? {
        try {
            return ValidProvider.valueOf(value.uppercase(Locale.getDefault())).providerBuilder
        } catch (e: IllegalArgumentException) {
            val message = String.format("Invalid value for option '--provider': %s.", value)
            throw IllegalArgumentException(message)
        }
    }
}