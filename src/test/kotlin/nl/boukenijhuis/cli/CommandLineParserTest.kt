package nl.boukenijhuis.cli

import nl.boukenijhuis.game.Hitchhiker
import nl.boukenijhuis.provider.Gemini
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import picocli.CommandLine
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class CommandLineParserTest {
    private lateinit var parser: CommandLineParser
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var errorStream: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream
    private lateinit var originalErr: PrintStream

    @BeforeEach
    fun setUp() {
        parser = CommandLineParser()

        // capture System.out and System.err for assertions
        originalOut = System.out
        originalErr = System.err
        outputStream = ByteArrayOutputStream()
        errorStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        System.setErr(PrintStream(errorStream))
    }

    @AfterEach
    fun tearDown() {
        // restore System.out and System.err
        System.setOut(originalOut)
        System.setErr(originalErr)
    }

    @Test
    fun testSetOnlyGame() {
        val result = CommandLine(parser).execute("--game", "zork")
        Assertions.assertNotEquals(0, result)
    }

    @Test
    fun testSetOnlyProvider() {
        val result = CommandLine(parser).execute("--provider", "gemini")
        Assertions.assertNotEquals(0, result)
    }

    @Test
    fun testSetBothGameAndProvider() {
        val result = CommandLine(parser).execute("--game", "hitchhiker", "--provider", "gemini")
        Assertions.assertEquals(0, result)

        val game = parser.game
        Assertions.assertNotNull(game)
        Assertions.assertTrue(game is Hitchhiker)

        val providerBuilder = parser.providerBuilder
        Assertions.assertNotNull(providerBuilder)
        Assertions.assertTrue(providerBuilder.model(parser.model).build() is Gemini)
    }

    @Test
    fun testInvalidGameThrowsException() {
        val exitCode = CommandLine(parser).execute("--game", "invalid")

        Assertions.assertNotEquals(0, exitCode)

        val output = outputStream.toString()
        val error = errorStream.toString()
        val combinedOutput = output + error

        // verify that the error message is present in one of the streams
        Assertions.assertTrue(combinedOutput.contains("Invalid value for option '--game'"))
    }

    @Test
    fun testRunMethod() {
        // The run method is required by Picocli but doesn't do anything
        // Just call it to ensure it doesn't throw exceptions
        parser.run()
    }
}
