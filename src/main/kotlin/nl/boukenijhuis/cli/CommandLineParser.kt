package nl.boukenijhuis.cli

import nl.boukenijhuis.cli.ProviderConverter.ValidProvider
import nl.boukenijhuis.game.Game
import nl.boukenijhuis.provider.ProviderBuilder
import picocli.CommandLine

@CommandLine.Command(sortOptions = false)
class CommandLineParser : Runnable {
    @CommandLine.Option(
        names = ["--game"],
        required = true,
        description = ["Supported games: \${COMPLETION-CANDIDATES}"],
        converter = [GameConverter::class],
        completionCandidates = GameConverter.ValidGame.CompletionCandidates::class
    )
    var game: Game? = null

    @CommandLine.Option(
        names = ["--provider"],
        required = true,
        description = ["Supported models: \${COMPLETION-CANDIDATES}"],
        converter = [ProviderConverter::class],
        completionCandidates = ValidProvider.CompletionCandidates::class
    )
    var providerBuilder: ProviderBuilder? = null

    @CommandLine.Option(names = ["--model"])
    var model: String? = null

    override fun run() {
        // picocli needs a runnable
    }
}
