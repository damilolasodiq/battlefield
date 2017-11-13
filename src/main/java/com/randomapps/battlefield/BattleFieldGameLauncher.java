package com.randomapps.battlefield;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.InvalidGameStateException;
import com.randomapps.battlefield.game.BattleFieldGame;
import com.randomapps.battlefield.game.Level;
import com.randomapps.battlefield.game.Player;
import com.randomapps.battlefield.layout.BattleField;
import com.randomapps.battlefield.util.GameHelper;

import java.util.Scanner;

public class BattleFieldGameLauncher {

    private static void validatePlayerName(String playerName) {
        if (playerName == null || playerName.length() < 2 || playerName.length() > 5) {
            System.out.println("Player name must be between 2 and 5 characters");
            return;
        }
    }

    private static void validateGameMode(String mode) {
        if (mode == null || mode.length() == 0 || (!mode.equals("1") && !mode.equals("2"))) {
            System.out.println("Press 1 to play with the CPU or 2 for multiple players");
            return;
        }
    }

    public static void main(String[] args) throws GameInitializationException {
        BattleFieldGame battleFieldGame = new BattleFieldGame();
        battleFieldGame.showInstructions();
        GameHelper.promptEnterKey();
        try {
            System.out.println("Press 1 to play with the CPU or 2 for multiple players");
            Scanner gameModeInput = new Scanner(System.in);
            String gameMode = gameModeInput.nextLine();
            validateGameMode(gameMode);

            Scanner player1NameInput = new Scanner(System.in);
            System.out.println("Player 1, Please a name for your Character\n");
            String player1Name = player1NameInput.nextLine();
            validatePlayerName(player1Name);
            Player player1 = new Player(player1Name);
            Player player2;
            if (gameMode.equals("2")) {
                Scanner player2NameInput = new Scanner(System.in);
                System.out.println("Player 2, Please a name for your Character\n");
                String player2Name = player2NameInput.nextLine();
                validatePlayerName(player2Name);
                player2 = new Player(player2Name);
            } else if (gameMode.equals("1")) {
                player2 = new Player("CPU", true);
            } else {
                throw new GameInitializationException("Could not determine game mode");
            }
            Level level = new Level(1);
            BattleField battleField = new BattleField(player1, player2, level);
            battleFieldGame.setBattleField(battleField);
            battleFieldGame.start();
            Scanner scanner = new Scanner(System.in);
            while (battleFieldGame.isActive()) {
                String next = scanner.nextLine();
                switch (next.toLowerCase()) {
                    case "help":
                        battleFieldGame.showInstructions();
                        break;
                    case "stat":
                        battleField.printStat();
                        break;
                    case "quit":
                    case "exit":
                        battleFieldGame.stop();
                        break;
                    case "pause":
                        battleFieldGame.pause();
                        break;
                    case "next level":
                        battleField = battleField.goToNextLevel();
                        if (!battleField.isRunning()) {
                            battleField.startGame();
                        }
                        break;
                    default:
                        try {
                            String[] split = next.split("\\s");
                            int playerRow = Integer.parseInt(split[0]);
                            int playerColumn = Integer.parseInt(split[1]);
                            int opponentRow = Integer.parseInt(split[2]);
                            int opponentColumn = Integer.parseInt(split[3]);
                            battleField.attack(playerRow, playerColumn, opponentRow, opponentColumn);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("The selected index does not exist. Please try again or type \"help\" to read teh game instructions");
                        }
                        break;
                }
            }
        } catch (GameInitializationException e) {
            System.err.println(e.getMessage());
        } catch (InvalidGameStateException e) {
            System.err.println(e.getMessage());
        }
    }
}
