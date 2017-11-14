package com.randomapps.battlefield.game;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.InvalidGameStateException;

import java.io.Serializable;

public interface Game<T extends Serializable> {

    void start() throws InvalidGameStateException, GameInitializationException;

    void stop()  throws InvalidGameStateException;

    void pause()  throws InvalidGameStateException;

    void resume()  throws InvalidGameStateException;

    boolean isActive();

    boolean isPaused();

    void showInstructions();

    SavedGame<T> lastSavedGame();
}
