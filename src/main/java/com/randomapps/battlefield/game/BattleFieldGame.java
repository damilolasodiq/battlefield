package com.randomapps.battlefield.game;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.InvalidGameStateException;
import com.randomapps.battlefield.layout.BattleField;
import com.randomapps.battlefield.util.GameHelper;

public class BattleFieldGame implements Game {

    private boolean active;
    private boolean paused;
    private BattleField battleField;


    @Override
    public void start() throws GameInitializationException {
        if (!this.isActive()) {
            this.active = true;
            if (this.battleField == null) {
                throw new GameInitializationException("No BattleField found to start game");
            }
            this.battleField.startGame();
        } else throw new GameInitializationException("A game is already in progress");
    }

    @Override
    public void stop() throws InvalidGameStateException {
        this.active = false;
        this.battleField.endGame();
        System.exit(0);
    }

    @Override
    public void pause() throws InvalidGameStateException {
        if (this.isActive())
            this.paused = true;
        else throw new InvalidGameStateException("You cannot paused a game that does not exist :)");

    }

    @Override
    public void resume() throws InvalidGameStateException {
        if (this.isActive())
            this.paused = false;
        else throw new InvalidGameStateException("You do not have any saved game");

    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isPaused() {
        return this.paused;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public void showInstructions() {
        String instructions = GameHelper.readFileFromClassPathResource("readme.txt");
        System.out.println(instructions);
    }
}
