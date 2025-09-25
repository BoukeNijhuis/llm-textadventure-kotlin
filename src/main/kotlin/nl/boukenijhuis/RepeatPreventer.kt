package nl.boukenijhuis

import nl.boukenijhuis.Printer.print

object RepeatPreventer {
    private var repeatCounter = 0
    private var repeatPhrase: String? = null

    fun updateOutputWhenTheGameKeepsRepeating(output: String): String {
        var output = output
        if (repeatPhrase != null && output.startsWith(repeatPhrase!!)) {
            if (++repeatCounter == 4) {
                output = "The game keeps repeating. So use the HELP command."
                // warn the spectator
                print("!!! The repeat counter tripped! With phrase: " + repeatPhrase + " !!!")

                // reset the counter & phrase
                resetCounterAndPhrase()
            }
        } else {
            resetCounterAndPhrase()
        }
        // update the repeat phrase
        repeatPhrase = output
        return output
    }

    private fun resetCounterAndPhrase() {
        repeatCounter = 0
        repeatPhrase = null
    }
}
