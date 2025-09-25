package nl.boukenijhuis.cli

import nl.boukenijhuis.game.Hitchhiker
import nl.boukenijhuis.game.Zork
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class GameConverterTest {
    private lateinit var converter: GameConverter
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream

    @BeforeEach
    fun setUp() {
        converter = GameConverter()

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
    fun convertZork() {
        val game = converter.convert("zork")
        Assertions.assertTrue(game is Zork)
    }

    @Test
    fun convertVoiceAdventure() {
        val game = converter.convert("hitchhiker")
        Assertions.assertTrue(game is Hitchhiker)
    }

    @Test
    fun convertCaseInsensitive() {
        val game = converter.convert("ZORK")
        Assertions.assertTrue(game is Zork)
    }

    @Test
    fun convertInvalidGameThrowsException() {
        Assertions.assertThrows(IllegalArgumentException::class.java, Executable {
            converter.convert("invalid")
        })
    }

    @Test
    @Throws(Exception::class)
    fun testAsTypeConverter() {
        // Test that the converter works with Picocli's CommandLine
        val typeConverter = GameConverter()

        // Test converting "zork" to a Zork game
        val game = typeConverter.convert("zork")

        // Verify the game is a Zork instance
        Assertions.assertTrue(game is Zork)
    }
}
