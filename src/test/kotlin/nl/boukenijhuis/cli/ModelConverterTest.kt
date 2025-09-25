package nl.boukenijhuis.cli

import nl.boukenijhuis.provider.Gemini
import nl.boukenijhuis.provider.Ollama
import nl.boukenijhuis.provider.ProviderBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class ModelConverterTest {
    private lateinit var converter: ProviderConverter
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream


    @BeforeEach
    fun setUp() {
        converter = ProviderConverter()

        // capture System.out for assertions
        originalOut = System.out
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        // restore System.out
        System.setOut(originalOut)
    }

    @Test
    fun convertGemini() {
        val providerBuilder = converter!!.convert("gemini")
        Assertions.assertEquals(providerBuilder, ProviderBuilder(Gemini::class.java))
    }

    @Test
    fun convertGeminiCaseInsensitive() {
        val providerBuilder = converter.convert("GEMINI")
        Assertions.assertEquals(providerBuilder, ProviderBuilder(Gemini::class.java))
    }

    // works only when you have the specified model downloaded
    @Test
    fun convertOllama() {
        val providerBuilder = converter.convert("ollama")
        Assertions.assertEquals(providerBuilder, ProviderBuilder(Ollama::class.java))
    }

    @Test
    fun convertInvalidModelThrowsException() {
        val exception: Exception? =
            Assertions.assertThrows<IllegalArgumentException?>(IllegalArgumentException::class.java, Executable {
                converter.convert("invalid")
            })
    }
}
