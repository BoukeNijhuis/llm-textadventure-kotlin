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
            ArrayList(
                Arrays.stream(entries.toTypedArray())
                    .map { p: ValidProvider -> p.name }
                    .map { s: String -> s.lowercase(Locale.getDefault()) }
                    .toList()))
    }

    override fun convert(value: String): ProviderBuilder {
        try {
            return ValidProvider.valueOf(value.uppercase(Locale.getDefault())).providerBuilder
        } catch (e: IllegalArgumentException) {
            val message = String.format("Invalid value for option '--provider': %s.", value)
            throw IllegalArgumentException(message)
        }
    }
}