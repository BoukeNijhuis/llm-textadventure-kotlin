package nl.boukenijhuis.cli

import nl.boukenijhuis.game.Game
import nl.boukenijhuis.game.Hitchhiker
import nl.boukenijhuis.game.Zork
import picocli.CommandLine.ITypeConverter
import java.util.*

class GameConverter : ITypeConverter<Game?> {
    enum class ValidGame(val game: Game) {
        ZORK(Zork()),
        HITCHHIKER(Hitchhiker());

        internal class CompletionCandidates : ArrayList<String?>(
            ArrayList<String?>(
                Arrays.stream<ValidGame?>(entries.toTypedArray())
                    .map<String?> { obj: ValidGame? -> obj!!.name }
                    .map<String?> { obj: String? -> obj!!.lowercase(Locale.getDefault()) }
                    .toList()))
    }

    override fun convert(value: String): Game? {
        val game = ValidGame.valueOf(value.uppercase(Locale.getDefault())).game
        return game
    }
}