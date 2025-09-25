package nl.boukenijhuis

import dev.langchain4j.chain.ConversationalChain
import nl.boukenijhuis.provider.Provider
import java.util.*
import java.util.function.Function

class CommandExtractor(private val model: Provider?, private val chain: ConversationalChain) {
    fun getCommand(modelInput: String?, doChecks: Boolean): String? {
        var modelInput = modelInput
        try {
            if (modelInput!!.isBlank()) {
                modelInput = NO_INPUT_MESSAGE
            }

            var command = chain.execute(modelInput)

            // remove new lines (the removing of <think> works better with no new lines)
            command = command.replace("\\n".toRegex(), " ")

            // remove <think>*</think>
            command = command.replace("<think>.*</think>".toRegex(), "").trim { it <= ' ' }

            if (doChecks) {
                // the order matters
                command = checkCommand(
                    command,
                    Function { x: String? ->
                        x!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size > 6
                    },
                    COMMAND_TOO_LONG_ERROR,
                    COMMAND_TOO_LONG_HINT,
                    chain
                )
                command = checkCommand(
                    command,
                    Function { x: String? -> x!!.indexOf("\"") > 1 || x.indexOf("'") > 1 },
                    QUOTES_FOUND_ERROR,
                    QUOTES_FOUND_HINT,
                    chain
                )
                command = checkCommand(
                    command,
                    Function { x: String? -> x!!.contains("*") },
                    FORMATTING_FOUND_ERROR,
                    FORMATTING_FOUND_HINT,
                    chain
                )
            }
            return command
        } catch (e: Exception) {
            try {
                return model?.handleException(e)
            } catch (ex: Exception) {
                throw e
            }
        }
    }

    companion object {
        private const val NO_INPUT_MESSAGE = "Did not receive any input from the game."
        private const val COMMAND_TOO_LONG_ERROR = "command too long"
        private const val COMMAND_TOO_LONG_HINT =
            "Your answer is too long. Please give me EXACTLY ONE short command in the form of GO EAST, OPEN DOOR, etc. Do not invent your own story, we are playing an existing game!"
        private const val QUOTES_FOUND_ERROR = "quotes found"
        private const val QUOTES_FOUND_HINT = "Do not use quotes. Give only simple commands without any formatting."
        private const val FORMATTING_FOUND_ERROR = "formatting found"
        private const val FORMATTING_FOUND_HINT =
            "Do not use formatting like *. Give only simple commands without any formatting."
        private const val WARNING_FORMAT = "!!! WARNING -> %s: %s !!!"

        private fun checkCommand(
            command: String,
            check: Function<String?, Boolean?>,
            errorMessage: String,
            hint: String?,
            chain: ConversationalChain
        ): String {
            if (check.apply(command)!!) {
                Printer.print(String.format(WARNING_FORMAT, errorMessage.uppercase(Locale.getDefault()), command))
                return chain.execute(hint)
            } else {
                return command
            }
        }
    }
}
