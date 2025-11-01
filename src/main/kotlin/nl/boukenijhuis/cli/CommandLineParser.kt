package nl.boukenijhuis.cli

import nl.boukenijhuis.cli.ProviderConverter.ValidProvider
import nl.boukenijhuis.game.Game
import nl.boukenijhuis.provider.ProviderBuilder
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(sortSynopsis = false, sortOptions = false)
class CommandLineParser : Runnable {
    @Option(
        names = ["--game"],
        required = true,
        description = ["Supported games: \${COMPLETION-CANDIDATES}"],
        converter = [GameConverter::class],
        completionCandidates = GameConverter.ValidGame.CompletionCandidates::class
    )
    lateinit var game: Game

    @Option(
        names = ["--provider"],
        required = true,
        description = ["Supported providers: \${COMPLETION-CANDIDATES}"],
        converter = [ProviderConverter::class],
        completionCandidates = ValidProvider.CompletionCandidates::class
    )
    lateinit var providerBuilder: ProviderBuilder

    @Option(names = ["--model"])
    var model: String = ""

    override fun run() {
        // picocli needs a runnable
    }
}
