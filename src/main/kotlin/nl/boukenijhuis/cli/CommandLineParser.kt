package nl.boukenijhuis.cli

import nl.boukenijhuis.cli.ProviderConverter.ValidProvider
import nl.boukenijhuis.game.Game
import nl.boukenijhuis.game.Zork
import nl.boukenijhuis.provider.Ollama
import nl.boukenijhuis.provider.ProviderBuilder
import picocli.CommandLine
import kotlin.jvm.javaClass

@CommandLine.Command(sortOptions = false)
class CommandLineParser : Runnable {
    @CommandLine.Option(
        names = ["--game"],
        required = true,
        description = ["Supported games: \${COMPLETION-CANDIDATES}"],
        converter = [GameConverter::class],
        completionCandidates = GameConverter.ValidGame.CompletionCandidates::class
    )
    lateinit var game: Game

    @CommandLine.Option(
        names = ["--provider"],
        required = true,
        description = ["Supported models: \${COMPLETION-CANDIDATES}"],
        converter = [ProviderConverter::class],
        completionCandidates = ValidProvider.CompletionCandidates::class
    )
    lateinit var providerBuilder: ProviderBuilder

    @CommandLine.Option(names = ["--model"])
    var model: String = ""

    override fun run() {
        // picocli needs a runnable
    }
}
