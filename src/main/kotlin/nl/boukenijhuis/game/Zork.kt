package nl.boukenijhuis.game;

import java.io.IOException;

public class Zork extends AbstractGame {

    @Override
    public void start() throws IOException {
        this.start(new String[]{"zork"});
    }

    @Override
    public String getCompletionString() {
        return "The game is over.";
    }
}
