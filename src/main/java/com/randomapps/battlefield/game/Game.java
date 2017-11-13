package com.randomapps.battlefield.game;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.InvalidGameStateException;

public interface Game {

    void start() throws InvalidGameStateException, GameInitializationException;

    void stop()  throws InvalidGameStateException;

    void pause()  throws InvalidGameStateException;

    void resume()  throws InvalidGameStateException;

    boolean isActive();

    boolean isPaused();

    void showInstructions();
}
