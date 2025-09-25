package nl.boukenijhuis.game

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

abstract class AbstractGame : Game {
    protected var gameOutput: InputStream? = null
    protected var gameInput: OutputStream? = null

    @Throws(IOException::class)
    protected fun start(commandArray: Array<String?>) {
        val processBuilder = ProcessBuilder()
        val game = processBuilder
            .redirectErrorStream(true)
            .command(*commandArray)
            .start()

        gameOutput = game.getInputStream()
        gameInput = game.getOutputStream()
    }

    override fun read(): String {
        var availableBytes: Int

        try {
            do {
                availableBytes = gameOutput!!.available()
                sleep()
            } while (availableBytes == 0)

            return clean(String(gameOutput!!.readNBytes(availableBytes)))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun sleep() {
        try {
            Thread.sleep(1)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    override fun writeAndRead(input: String): String {
        writeInput(input)
        return clean(this.read())
    }

    private fun clean(s: String): String {
        return s.replace(">", "")
            .trim { it <= ' ' }
    }

    private fun writeInput(input: String?) {
        try {
            gameInput!!.write((input + System.lineSeparator()).toByteArray())
            gameInput!!.flush()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
