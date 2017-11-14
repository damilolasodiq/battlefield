package com.randomapps.battlefield;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.InvalidGameStateException;
import com.randomapps.battlefield.game.BattleFieldGame;
import com.randomapps.battlefield.game.Level;
import com.randomapps.battlefield.game.Player;
import com.randomapps.battlefield.game.SavedGame;
import com.randomapps.battlefield.layout.BattleField;
import com.randomapps.battlefield.util.GameHelper;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.function.Predicate;

public class BattleFieldGameLauncher {

    private static boolean validatePlayerName(String playerName) {
        if (playerName == null || playerName.length() < 2 || playerName.length() > 5) {
            System.out.println("Player name must be between 2 and 5 characters");
            return false;
        }
        return true;
    }

    private static boolean validateGameMode(String mode) {
        if (mode == null || mode.length() == 0 || (!mode.equals("1") && !mode.equals("2"))) {
            System.out.println("Press 1 to play with the CPU or 2 for multiple players");
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws GameInitializationException {
        BattleFieldGame battleFieldGame = new BattleFieldGame();
        BattleField battleField;
        SavedGame<BattleField> battleFieldSavedGame = battleFieldGame.lastSavedGame();
        String resumeGame = null;
        if (battleFieldSavedGame != null) {
            String prompt = "Type \"1\" to resume or \"2\" to start a new game";
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(battleFieldSavedGame.getDateSaved());
            resumeGame = getInputAsString(String.format("You have a saved game on %s, %s", date, prompt), true,s -> {
                if (s == null || s.length() == 0 || (!s.equals("1") && !s.equals("2"))) {
                    System.out.println(prompt);
                    return false;
                }
                return true;
            });
        }
        if (resumeGame == null || resumeGame.equals("2")) {
            battleFieldGame.showInstructions();

            GameHelper.pressAnyKeyToContinue();
            GameHelper.clearConsole();

            String gameMode = getInputAsString("Press 1 to play with the CPU or 2 for multiple players",true, BattleFieldGameLauncher::validateGameMode);

            String player1Name = getInputAsString("Player 1, Please a name for your Character",true, BattleFieldGameLauncher::validatePlayerName);

            Player player1 = new Player(player1Name);
            Player player2;
            if (gameMode.equals("2")) {
                String player2Name = getInputAsString("Player 2, Please a name for your Character",true, BattleFieldGameLauncher::validatePlayerName);
                player2 = new Player(player2Name);
            } else if (gameMode.equals("1")) {
                player2 = new Player(true);
            } else {
                throw new GameInitializationException("Could not determine game mode");
            }
            Level level = new Level(1);
            battleField = new BattleField(player1, player2, level);
        } else {
            battleField = battleFieldSavedGame.getGame();
        }

        try {
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
                        if (next != null && next.matches("^\\d{4}")) {
                            try {
                                char[] split = next.toCharArray();
                                int playerRow = Character.getNumericValue(split[0]);
                                int playerColumn = Character.getNumericValue(split[1]);
                                int opponentRow = Character.getNumericValue(split[2]);
                                int opponentColumn = Character.getNumericValue(split[3]);
                                battleField.attack(playerRow, playerColumn, opponentRow, opponentColumn);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.err.println("The selected index does not exist. Please try again or type \"help\" to read teh game instructions");
                            } catch (Exception e) {
                                System.err.println("An error occurred. Please try again or type \"help\" to read teh game instructions");
                            }
                        } else {
                            System.err.println("Unknown command. Please try again or type \"help\" to read teh game instructions");
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

    private static String getInputAsString(final String prompt,boolean clearConsole, Predicate<String> stringPredicate) {
        System.out.println(prompt);
        boolean b = false;
        String output = null;
        while (!b) {
            Scanner s = new Scanner(System.in);
            output = s.nextLine();
            b = stringPredicate.test(output);
        }
        if(clearConsole)
            GameHelper.clearConsole();
        return output;
    }
}
