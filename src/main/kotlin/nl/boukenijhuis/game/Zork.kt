package nl.boukenijhuis.game

import java.io.IOException

class Zork : AbstractGame() {
    @Throws(IOException::class)
    override fun start() {
        this.start(arrayOf<String>("zork"))
    }

    override val completionString: String = "The game is over."
}
