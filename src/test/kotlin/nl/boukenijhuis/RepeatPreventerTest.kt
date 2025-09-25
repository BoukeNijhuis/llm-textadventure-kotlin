package nl.boukenijhuis

import nl.boukenijhuis.RepeatPreventer.updateOutputWhenTheGameKeepsRepeating
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class RepeatPreventerTest {
    @Test
    fun happyFlow() {
        val input = "input"
        var output: String
        (0..3).forEach { i ->
            output = updateOutputWhenTheGameKeepsRepeating(input)
            Assertions.assertEquals(output, input)
        }
        output = updateOutputWhenTheGameKeepsRepeating(input)
        Assertions.assertNotEquals(input, output)
    }
}