package nl.boukenijhuis

import nl.boukenijhuis.game.Game
import nl.boukenijhuis.provider.Provider
import java.util.*

object Printer {
    fun printStatus(game: Game?, provider: Provider?) {
        val message =
            "Game:     " + game?.name + System.lineSeparator() +
                    "Provider: " + provider?.javaClass?.simpleName + System.lineSeparator() +
                    "Model:    " + capitalize(provider?.model)
        kotlin.io.print(message)
    }

    private fun capitalize(input: String?): String? {
        if (input == null || input.isBlank()) {
            return input
        }
        return input.substring(0, 1).uppercase(Locale.getDefault()) + input.substring(1).lowercase(Locale.getDefault())
    }

    @JvmStatic
    fun print(message: String?) {
        System.out.printf("\n\n%s", message)
    }
}
