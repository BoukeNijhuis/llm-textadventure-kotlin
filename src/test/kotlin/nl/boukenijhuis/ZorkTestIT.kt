package nl.boukenijhuis

import nl.boukenijhuis.game.Zork
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

internal class ZorkTestIT {
    @Test
    @Throws(IOException::class)
    fun writeAndRead() {
        val zork = Zork()
        zork.start()
        zork.read()
        val output = zork.writeAndRead("Open mailbox")
        val expected = """
                Opening the mailbox reveals:
                  A leaflet.
                  """.trimIndent()
        Assertions.assertEquals(expected, output)
    }

    @Test
    @Throws(IOException::class)
    fun read() {
        val zork = Zork()
        zork.start()
        val output = zork.read()
        val expected = """
                Welcome to Dungeon.			This version created 11-MAR-91.
                You are in an open field west of a big white house with a boarded
                front door.
                There is a small mailbox here.
                """.trimIndent()
        Assertions.assertEquals(expected, output)
    }
}