package nl.boukenijhuis

import dev.langchain4j.chain.ConversationalChain
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import nl.boukenijhuis.cli.CommandLineParser
import picocli.CommandLine
import java.io.IOException
import java.util.*

object Main {
    // TODO: readme with instructions for Zork, DumbFrotz & all models
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val clParser = CommandLineParser()
        val result = CommandLine(clParser).execute(*args)

        // exit if there was an error
        if (result != 0) {
            System.exit(result)
        }

        val game = clParser.game!!
        val providerBuilder = clParser.providerBuilder
        val provider = providerBuilder?.model(clParser.model)?.build()

        Printer.printStatus(game, provider)

        // setup the chain
        val chain = ConversationalChain.builder()
            .chatModel(provider?.chatModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(50))
            .build()

        // initial prompt
        var modelInput: String = """
                I would like to play a text adventure with you.
                I provide the descriptions and you will provide ONLY ONE command in the form of OPEN DOOR, GO WEST, etc. 
                Do not use formatting. 
                Try not to die. 
                Use the hints that the game gives you.
                
                """.trimIndent()

        val commandExtractor = CommandExtractor(provider, chain)
        // do not check the provided instructions
        commandExtractor.getCommand(modelInput, false)

        // init game
        game.start()
        modelInput = game.read()
        Printer.print(modelInput)

        // game loop
        while (true) {
            val command = commandExtractor.getCommand(modelInput, true)

            if (command != null) {
                Printer.print(command.uppercase(Locale.getDefault()))
                modelInput = game.writeAndRead(command)
                Printer.print(modelInput)

                // check if the game is over
                if (modelInput.contains(game.completionString)) {
                    System.exit(0)
                }

                // help the llm when it is stuck
                modelInput = RepeatPreventer.updateOutputWhenTheGameKeepsRepeating(modelInput)
            }
        }
    }
}
