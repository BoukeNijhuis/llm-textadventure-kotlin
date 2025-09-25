package nl.boukenijhuis.game

import java.io.IOException

class Hitchhiker : AbstractGame() {
    @Throws(IOException::class)
    override fun start() {
        this.start(arrayOf("dfrotz", "/Users/boukenijhuis/hitchhiker-invclues-r31-s871119.z5"))
    }

    override val completionString: String= "The game is over."
}
