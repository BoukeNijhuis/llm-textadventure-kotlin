package nl.boukenijhuis

import dev.langchain4j.chain.ConversationalChain
import nl.boukenijhuis.provider.Provider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class CommandExtractorTest {
    var model: Provider = Mockito.mock(Provider::class.java)
    var chain: ConversationalChain = Mockito.mock(ConversationalChain::class.java)
    var commandExtractor: CommandExtractor = CommandExtractor(model, chain)

    @Test
    fun emptyInput() {
        Mockito.`when`(chain.execute(NO_INPUT_MESSAGE)).thenReturn(NO_INPUT_MESSAGE)

        val command = commandExtractor.getCommand("", false)
        Assertions.assertEquals(NO_INPUT_MESSAGE, command)
    }

    @Test
    fun withoutChecks() {
        val input = "input"
        val llmAnswer = "LLM answer"
        Mockito.`when`(chain.execute(input)).thenReturn(llmAnswer)

        val command = commandExtractor.getCommand(input, false)
        Assertions.assertEquals(llmAnswer, command)
    }

    @Test
    fun commandTooLong() {
        val input = "input"
        Mockito.`when`(chain.execute(input)).thenReturn("a command that is too long for sure")
        val improvedAnswer = "improved answer"
        Mockito.`when`(chain.execute(COMMAND_TOO_LONG_HINT)).thenReturn(improvedAnswer)

        val command = commandExtractor.getCommand(input, true)
        Assertions.assertEquals(improvedAnswer, command)
    }

    @Test
    fun quotesFound() {
        val input = "input"
        Mockito.`when`(chain.execute(input)).thenReturn("an answer containing \"quotes\".")
        val improvedAnswer = "improved answer"
        Mockito.`when`(chain.execute(QUOTES_FOUND_HINT)).thenReturn(improvedAnswer)

        val command = commandExtractor.getCommand(input, true)
        Assertions.assertEquals(improvedAnswer, command)
    }

    @Test
    fun formattingFound() {
        val input = "input"
        Mockito.`when`(chain.execute(input)).thenReturn("an answer containing *formatting*.")
        val improvedAnswer = "improved answer"
        Mockito.`when`(chain.execute(FORMATTING_FOUND_HINT)).thenReturn(improvedAnswer)

        val command = commandExtractor.getCommand(input, true)
        Assertions.assertEquals(improvedAnswer, command)
    }

    @Test
    fun removeThinkingPart() {
        val input = "input"
        Mockito.`when`(chain.execute(input)).thenReturn("<think>thinking</think> command")

        val command = commandExtractor.getCommand(input, true)
        Assertions.assertEquals("command", command)
    }

    @Test
    fun removeLongThinkingPart() {
        val input = "input"
        Mockito.`when`<String?>(chain.execute(input))
            .thenReturn("<think>\nOkay, the user wants to play a text adventure game. They mentioned starting in a field west of a big white house with a boarded front door and a mailbox. The goal is to provide only one command each time.\n\nFirst, I need to think about typical text adventure mechanics. The mailbox is mentioned, so checking it is a common first step. The player might expect to find something inside, like a key or a note, which could be useful later. Since the front door is boarded up, maybe there's another way into the house, but the mailbox is the immediate interactable object here.\n\nI should make sure the command is straightforward. The user might want to examine the mailbox first. The command \"OPEN MAILBOX\" makes sense here. It's a logical first action to explore the environment and gather items or clues. There's no immediate danger mentioned, so this action should be safe. Let's go with that.\n</think>\n\nOPEN MAILBOX")

        val command = commandExtractor.getCommand(input, true)
        Assertions.assertEquals("OPEN MAILBOX", command)
    }


    companion object {
        private const val NO_INPUT_MESSAGE = "Did not receive any input from the game."
        private const val COMMAND_TOO_LONG_HINT =
            "Your answer is too long. Please give me EXACTLY ONE short command in the form of GO EAST, OPEN DOOR, etc. Do not invent your own story, we are playing an existing game!"
        private const val QUOTES_FOUND_HINT = "Do not use quotes. Give only simple commands without any formatting."
        private const val FORMATTING_FOUND_HINT =
            "Do not use formatting like *. Give only simple commands without any formatting."
    }
}
