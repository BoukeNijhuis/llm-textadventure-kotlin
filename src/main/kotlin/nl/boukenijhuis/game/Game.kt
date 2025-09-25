package nl.boukenijhuis.game

import java.io.IOException

interface Game {
    @Throws(IOException::class)
    fun start()

    fun read(): String

    fun writeAndRead(input: String): String

    val completionString: String

    val name: String
        get() = this.javaClass.getSimpleName()
}
