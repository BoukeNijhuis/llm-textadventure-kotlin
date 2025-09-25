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
            ArrayList(
                Arrays.stream(entries.toTypedArray())
                    .map { g: ValidGame -> g.name }
                    .map { s: String -> s.lowercase(Locale.getDefault()) }
                    .toList()))
    }

    override fun convert(value: String): Game =
        ValidGame.valueOf(value.uppercase(Locale.getDefault())).game
}