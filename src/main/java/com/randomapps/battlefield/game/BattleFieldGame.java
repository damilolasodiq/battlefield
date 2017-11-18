package com.randomapps.battlefield.game;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.InvalidGameStateException;
import com.randomapps.battlefield.layout.BattleField;
import com.randomapps.battlefield.util.GameHelper;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;

public class BattleFieldGame implements Game<BattleField> {

    private boolean active;
    private boolean paused;
    private BattleField battleField;

    private static final String GAME_SAVE_FOLDER = "BattleField";
    private static final String GAME_SAVE_PREFIX = "battlefield-";

    private static File getGameSaveFolder() {
        return new File(System.getProperty("java.io.tmpdir") + "/" + GAME_SAVE_FOLDER);
    }

    @Override
    public void stop() throws InvalidGameStateException {
        this.active = false;
        this.battleField.endGame();
        System.exit(0);
    }

    @Override
    public void start() throws GameInitializationException, InvalidGameStateException {
        if (!this.isActive()) {
            this.active = true;
            if (this.battleField == null) {
                throw new GameInitializationException("No BattleField found to start game");
            }
            if (this.isPaused())
                this.resume();
            else
                this.battleField.startGame();
        } else throw new GameInitializationException("A game is already in progress");
    }

    @Override
    public void resume() throws InvalidGameStateException {
        if (this.isPaused()) {
            this.paused = false;
            this.battleField.redrawField();
        }
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

    @Override
    public void pause() throws InvalidGameStateException {
        if (this.isActive()) {
            FileOutputStream fout = null;
            try {
                File dir = getGameSaveFolder();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File tempFile = File.createTempFile(GAME_SAVE_PREFIX, ".ser", dir);
                fout = new FileOutputStream(tempFile);
                try (ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                    SavedGame<BattleField> battleFieldSavedGame = new SavedGame<>();
                    battleFieldSavedGame.setDateSaved(new Date());
                    battleFieldSavedGame.setGame(this.battleField);
                    battleFieldSavedGame.setDescription(String.format("Game play between %s and %s", this.battleField.getCurrentPlayer().getName(), this.battleField.getOpponent().getName()));
                    oos.writeObject(battleFieldSavedGame);
                    this.paused = true;
                    System.out.println("Your game has been paused. You would get a prompt to resume next time you play.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fout != null) {
                        fout.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else throw new InvalidGameStateException("You cannot paused a game that does not exist :)");

    }

    @Override
    public SavedGame<BattleField> lastSavedGame() {
        try {
            File file = getGameSaveFolder();
            if (file != null) {
                File[] files = file.listFiles();
                if (files == null || files.length < 1) {
                    return null;
                } else {
                    File gameFile = files[0];
                    FileInputStream fileInputStream = new FileInputStream(gameFile);
                    try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                        SavedGame savedGame = (SavedGame) objectInputStream.readObject();
                        this.paused = true;
                        return savedGame;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    } finally {
                        try {
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Files.delete(gameFile.toPath());
                    }
                }
            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }
}
