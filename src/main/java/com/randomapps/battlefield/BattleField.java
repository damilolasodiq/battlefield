package com.randomapps.battlefield;

import com.randomapps.battlefield.barrack.Arsenal;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.barrack.armory.WeaponFactory;
import com.randomapps.battlefield.barrack.army.Soldier;
import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.exception.SoldierOutOfArmorException;
import com.randomapps.battlefield.exception.WeaponNotAssignableException;
import com.randomapps.battlefield.exception.WeaponNotAvailableException;
import com.randomapps.battlefield.game.Level;
import com.randomapps.battlefield.game.Player;
import com.randomapps.battlefield.game.PlayerStat;
import com.randomapps.battlefield.util.GameHelper;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class BattleField implements Serializable {

    private final static Character DEAD_SOLDIER_POSITION = '!';
    private final static Character HIDDEN_POSITION = '-';
    private final static Character BLASTED_POSITION = 'X';

    Player[] players = new Player[2]; //number of players possible at a time
    private Level level;
    private int currentPlayerIndex = 0;
    private boolean running;


    public BattleField(Player player1, Player player2, Level level) throws GameInitializationException {
        GameHelper.clearConsole();
        this.level = level;
        players[0] = player1;
        players[1] = player2;
        this.running = true;
        this.initializePlayers();
        this.drawBattleHeaders();
        this.drawField();
    }

    private int calculatePoints(Soldier shootingSoldier, Soldier shotSoldier, Weapon weapon) {
        int point = weapon.scorePoint();
        if (shootingSoldier.getRank() < shotSoldier.getRank()) {
            point += 2 * this.level.getLevel();
        }
        return point;
    }

    public boolean isRunning() {
        return running;
    }

    private Player getFirstPlayer() {
        return this.players[0];
    }

    private Player getSecondPlayer() {
        return this.players[1];
    }

    private void drawBattleField() {
        BattlePosition[][] area = this.getFirstPlayer().getBattleArea().getArea();
        BattlePosition[][] area1 = this.getSecondPlayer().getBattleArea().getArea();
        for (int x = 1; x < area[0].length + 1; x++) {
            System.out.print("   " + (x - 1) + "");
        }
        System.out.printf("%6s", " ");
        for (int x = 1; x < area[0].length + 1; x++) {
            System.out.print("   " + (x - 1) + "");
        }

        System.out.println();
        for (int j = 0; j < area.length; j++) {
            System.out.print(j);
            boolean hideSoldiers = this.getCurrentPlayer() == this.getFirstPlayer();
            printBattleArea(area[j], !hideSoldiers);

            System.out.printf("%5s", " ");

            printBattleArea(area1[j], hideSoldiers);
            System.out.println();

        }
    }

    private void printBattleArea(BattlePosition[] area, boolean hideSoldiers) {
        int len = area.length - 1;
        for (int i = 0; i < area.length; i++) {
            Character rowToken;
            BattlePosition battlePosition = area[i];
            Soldier soldier = battlePosition.getSoldier();
            if (soldier != null) {
                if (soldier.isAlive()) {
                    rowToken = hideSoldiers ? HIDDEN_POSITION : soldier.getType().getSymbol();
                } else {
                    rowToken = DEAD_SOLDIER_POSITION;
                }
            } else {
                rowToken = battlePosition.isBlasted() ? BLASTED_POSITION : HIDDEN_POSITION;
            }
            System.out.print((i == 0 ? "[ " : "| ") + rowToken + " ");
            if (len == i)
                System.out.print("]");

        }

    }

    private final void initializePlayers() throws GameInitializationException {
        for (int i = 0; i < players.length; i++) {
            BattleArea battleArea = new BattleArea(this.level);
            Player player = players[i];
            player.setBattleArea(battleArea);
            initializePlayer(player);
            initializeLevel(player);
        }

    }

    public void initializeLevel(Player player) {
        BattlePosition[][] area = player.getBattleArea().getArea();
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                if (area[i][j] == null)
                    area[i][j] = new BattlePosition();
            }
        }
    }

    public void positionSoldiers(Player player) {
        BattlePosition[][] area = player.getBattleArea().getArea();
        SecureRandom secureRandomX = new SecureRandom();
        SecureRandom secureRandomY = new SecureRandom();
        Queue<Soldier> soldiers = new LinkedList(player.getSoldiers());
        while (!soldiers.isEmpty()) {
            Soldier s = soldiers.peek();
            int row = secureRandomX.nextInt(this.level.getRow());
            int col = secureRandomY.nextInt(this.level.getColumn());
            if (area[row][col] == null) {
                int[][] coordinate = new int[1][2];
                coordinate[0][0] = row;
                coordinate[0][1] = col;
                s.setBattleCoordinate(coordinate);
                area[row][col] = new BattlePosition(s);
                soldiers.remove();
            }
        }
    }

    private void drawField() {
        this.drawBattleField();
    }

    private void initializePlayer(Player player) throws GameInitializationException {
        Arsenal arsenal = new Arsenal();

        List<Soldier> soldiers = new ArrayList<>();
        this.level.getSoldiers().stream().forEach(s -> soldiers.add(s.clone()));
        List<WeaponType> weapons = new ArrayList<>();
        this.level.getWeaponTypes().stream().forEach(w -> weapons.add(w));
        if (weapons != null) {

            Map<WeaponType, List<WeaponType>> collect = weapons.stream().collect(Collectors.groupingBy(weapon -> weapon));

            for (Soldier s : soldiers) {
                for (WeaponType weaponType : collect.keySet()) {
                    List<WeaponType> availableWeapons = collect.get(weaponType);
                    if (!s.allowedWeapons().isEmpty() && s.allowedWeapons().contains(weaponType) && !availableWeapons.isEmpty()) {
                        try {
                            Weapon weapon = WeaponFactory.newInstance(availableWeapons.remove(0));
                            s.assignWeapon(weapon);
                            break;
                        } catch (WeaponNotAssignableException e) {
                            throw new GameInitializationException(e);
                        }
                    }
                }
                if (!s.getCurrentWeapon().isPresent()) {
                    throw new GameInitializationException("You cannot take a Soldier to a Battle without a Weapon now can you?");
                }
            }
            //add the rest of the weapons to the armory
            collect.forEach((k, v) -> arsenal.addWeapon(k, v.size()));
        }
        player.setStat(new PlayerStat());
        player.setArsenal(arsenal);
        player.setSoldiers(soldiers);
        positionSoldiers(player);
    }

    public Player getCurrentPlayer() {
        return this.players[currentPlayerIndex];
    }

    public Player getOpponent() {
        return this.players[1 ^ currentPlayerIndex];
    }

    private void nextPlayerTurn() {
        this.currentPlayerIndex = 1 ^ currentPlayerIndex;
        if (this.getCurrentPlayer().isCpu()) {
            this.simulateCPUPlay(0);
        } else {
            this.drawBattleHeaders();
            this.drawField();
        }

    }


    private void simulateCPUPlay(int count) {
        Player player = this.getCurrentPlayer();
        List<BattlePosition> battlePositionsWithActiveSoldiers = player.getBattleArea().getBattlePositionsWithActiveSoldiers();
        if (this.level.getLevel() > 1) {
            //cpu gets smarter from level 2 by ensuring it uses the best Soldiers first
            int max = battlePositionsWithActiveSoldiers.stream().mapToInt(x -> x.getSoldier().getRank()).max().getAsInt();
            battlePositionsWithActiveSoldiers = battlePositionsWithActiveSoldiers.stream().filter(x -> x.getSoldier().getRank() == max).collect(Collectors.toList());
        }
        SecureRandom rnd = new SecureRandom();
        int i = rnd.nextInt(battlePositionsWithActiveSoldiers.size());
        BattlePosition attackPosition = battlePositionsWithActiveSoldiers.get(i);
        Soldier soldier = attackPosition.getSoldier();
        int x = soldier.getBattleCoordinate()[0][0];
        int y = soldier.getBattleCoordinate()[0][1];
        if (!soldier.getCurrentWeapon().isPresent() || soldier.getCurrentWeapon().get().isOutOfArmor()) {
            try {
                this.assignWeapon(x, y);
            } catch (WeaponNotAssignableException | WeaponNotAvailableException e) {
                if (count < this.level.getLevel() + 2) { //after the x iteration, let the CPU play with whatever player it has selected
                    simulateCPUPlay(count + 1);
                }
            }
        }
        SecureRandom secureRandomX = new SecureRandom();
        SecureRandom secureRandomY = new SecureRandom();
        int row = secureRandomX.nextInt(this.level.getRow());
        int col = secureRandomY.nextInt(this.level.getColumn());
        this.attack(x, y, row, col);
    }

    private void printNamesInHeaders() {
        for (int i = 0; i < this.players.length; i++) {
            Player player = this.players[i];
            int fullHealthBar = (this.level.getColumn() * 4) + 2;
            int startPlayerName = fullHealthBar - 4;
            System.out.printf("%-" + startPlayerName + "s %d%%", player.getName().toUpperCase(), this.calculateHealthInPercentage(player));
            System.out.printf("%5s", " ");
        }
        System.out.println();
    }

    private void drawBattleHeaders() {
        System.out.printf(String.format("BattleField Level %d \n", this.level.getLevel()));
        System.out.printf("%-15s %s \n", "Current Player", this.getCurrentPlayer().getName().toUpperCase());
        this.printNamesInHeaders();

        int fullHealthBar = (this.level.getColumn() * 4) + 2;

        for (int i = 0; i < this.players.length; i++) {
            int playerHealth = this.players[i].getHealth();
            int currentPlayerHealthBar = (int) Math.floor((playerHealth / (double) this.level.getMaxHealthInLevel()) * fullHealthBar);
            for (int j = 0; j < fullHealthBar; j++) {
                if (j > currentPlayerHealthBar) {
                    System.out.print(" ");
                } else {
                    System.out.print("=");
                }
            }
            System.out.printf("%5s", " ");
        }
        System.out.println();
    }


    public void attack(int playerRow, int playerColumn, int opponentRow, int opponentColumn) {
        BattlePosition[][] area = this.getCurrentPlayer().getBattleArea().getArea();
        BattlePosition[][] opponentArea = this.getOpponent().getBattleArea().getArea();
        BattlePosition battlePosition = area[playerRow][playerColumn];
        if (battlePosition != null && battlePosition.getSoldier() != null) {
            Soldier soldier = battlePosition.getSoldier();
            if (!soldier.isAlive()) {
                System.err.println("Sorry, your Soldier at that position has fallen!");
                return;
            }
            Optional<Weapon> weaponOptional = soldier.getCurrentWeapon();
            if (weaponOptional.isPresent()) {
                try {
                    Weapon weapon = weaponOptional.get();
                    weapon.fire();
                    if (this.level.getColumn() / weapon.getWeaponGrade().getGrade() >= opponentColumn) {
                        BattlePosition opponentBattlePosition = opponentArea[opponentRow][opponentColumn];
                        Soldier opponentSoldier = opponentBattlePosition.getSoldier();
                        if (opponentSoldier != null) {
                            if (opponentSoldier.isAlive()) {
                                System.out.printf("%s %s has hit the enemy's %s\n", this.getCurrentPlayer().getName(), soldier.getType().name(), opponentSoldier.getType().name());
                                opponentSoldier.takeHit(weaponOptional.get());
                                if (!opponentSoldier.isAlive()) {
                                    this.getCurrentPlayer().getStat().setNumberOfEnemiesKilled(this.getCurrentPlayer().getStat().getNumberOfEnemiesKilled() + 1);
                                    this.getOpponent().getStat().setNumberOfSoldiersKilled(this.getCurrentPlayer().getStat().getNumberOfEnemiesKilled());
                                    this.getCurrentPlayer().getStat().setPoints(this.getCurrentPlayer().getStat().getPoints() + calculatePoints(soldier, opponentSoldier, weaponOptional.get()));
                                }
                            }
                        } else {
                            System.out.printf("%s %s has hit an empty position on the enemy's area.\n", this.getCurrentPlayer().getName(), soldier.getType().name());
                            opponentBattlePosition.setBlasted(true);
                        }
                        if (this.shouldGameEnd()) {
                            System.out.printf("%s has WON!!\n", this.getCurrentPlayer().getName());
                            if (this.level.getLevel() < Level.MAX_LEVEL) {
                                System.out.printf("Type \"NEXT LEVEL\" to go to %d \n", this.level.getLevel() + 1);
                            } else {
                                System.out.printf("You have reached the end of the game!. The game would now exit :)\n");
                                this.endGame();
                                System.exit(0);
                            }
                            this.endGame();
                            return;
                        }
                    } else {
                        System.out.printf("%s %s shot out of range. What a waste! \n", this.getCurrentPlayer().getName(), soldier.getType().name());
                    }
                    this.nextPlayerTurn();
                } catch (SoldierOutOfArmorException e) {
                    System.err.println("The Soldier you have selected is out of ArmorVest use a different Soldier or pick a new Weapon in the Arsenal.");
                    return;
                }
            } else {
                System.err.println("The Soldier you have selected does not have a Weapon. Check the Arsenal for weapons.");
            }
        } else {
            System.err.println("You do not have a Soldier at that battlePosition!");
        }
    }

    private boolean shouldGameEnd() {
        Arsenal arsenal = this.getOpponent().getArsenal();
        if (this.getOpponent().getHealth() <= 0) {
            return true;
        }

        long numberOfSoldiersWithWeapon = this.getOpponent().getSoldiers().stream().filter(s -> {
            Optional<Weapon> currentWeapon = s.getCurrentWeapon();
            if (s.isAlive() && currentWeapon.isPresent()) {
                if (!currentWeapon.get().isOutOfArmor()) {
                    return true;
                }
            }
            return false;
        }).count();

        if (numberOfSoldiersWithWeapon < 1 && arsenal != null && !arsenal.isEmpty()) {
            Map<WeaponType, Integer> inventory = arsenal.getInventory();

            List<Soldier> soldierWithoutWeapon = this.getOpponent().getSoldiers().stream().filter(s -> {
                Optional<Weapon> currentWeapon = s.getCurrentWeapon();
                if (s.isAlive() && currentWeapon.isPresent()) {
                    if (currentWeapon.get().isOutOfArmor()) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());

            if (soldierWithoutWeapon.isEmpty()) {
                return false;
            }
            for (Soldier s : soldierWithoutWeapon) {
                boolean foundMatchingWeapon = false;
                for (WeaponType weaponType : inventory.keySet()) {
                    int count = inventory.get(weaponType);
                    if (count > 0) {
                        if (s.canUseWeapon(weaponType)) {
                            inventory.put(weaponType, count - 1);
                            foundMatchingWeapon = true;
                            break;
                        }
                    }
                }
                if (!foundMatchingWeapon)
                    return true;
            }
            return true;
        }
        return false;
    }

    public void assignWeapon(int playerRow, int playerCol) throws WeaponNotAssignableException, WeaponNotAvailableException {
        BattlePosition battlePosition = this.getCurrentPlayer().getBattleArea().getArea()[playerRow][playerCol];
        Soldier soldier = battlePosition.getSoldier();
        if (soldier != null) {
            if (soldier.isAlive()) {
                Arsenal arsenal = this.getCurrentPlayer().getArsenal();
                boolean assigned = false;
                for (WeaponType weaponType : soldier.allowedWeapons()) {
                    if (arsenal.hasWeapon(weaponType)) {
                        Weapon weapon = arsenal.pickWeapon(weaponType);
                        soldier.assignWeapon(weapon);
                        assigned = true;
                        break;
                    }
                }
                if (!assigned)
                    throw new WeaponNotAssignableException("Could not find an appropriate Weapon for the Soldier in the arsenal");
            } else {
                throw new WeaponNotAssignableException("Cannot assign a weapon to a dead Soldier");
            }
        } else {
            throw new WeaponNotAssignableException("Cannot find a Soldier in the specified battle position");
        }
    }

    private void printCurrentPlayerStat() {
        this.printStat(this.getCurrentPlayer());
    }

    public void printStat(Player... players) {
        if (players == null || players.length < 1) {
            this.printCurrentPlayerStat();
            return;
        }
        for (Player player : players) {
            System.out.printf("_________________________________\n");
            System.out.printf("%-25s %s\n", "Player", player.getName().toUpperCase());
            System.out.printf("%-25s %d \n", "Enemies Killed", player.getStat().getNumberOfEnemiesKilled());
            System.out.printf("%-25s %d \n", "Soldiers Killed", player.getNumberOfDeadSoldiers());
            System.out.printf("%-25s %d \n", "Soldiers Injured", player.getNumberOfInjuredSoldiers());
            System.out.printf("%-25s %d%%\n", "Health Status", this.calculateHealthInPercentage(player));
            System.out.printf("%-25s %d \n", "Points", player.getStat().getPoints());
            System.out.printf("-------Weapons in Arsenal--------\n");
            player.getArsenal().getInventory().forEach((k, v) -> System.out.printf("%-25s %d \n", k, v));
            System.out.printf("_________________________________\n");
        }
    }

    private int calculateHealthInPercentage(Player player) {
        double playerHealth = player.getHealth();
        double maxHealth = this.level.getMaxHealthInLevel();
        return (int) Math.floor((playerHealth / maxHealth) * 100);
    }

    public void endGame() {
        this.printStat(this.getFirstPlayer(), this.getSecondPlayer());
        this.running = false;
    }

    public void startGame() {
        this.running = true;
    }

    public BattleField goToNextLevel() throws GameInitializationException {
        if (!this.running) {
            return new BattleField(this.getFirstPlayer(), this.getSecondPlayer(), new Level(this.level.getLevel() + 1));
        } else {
            throw new GameInitializationException("You cannot switch game level while another game is going on");
        }
    }

    public void redrawField() {
        this.drawBattleHeaders();
        this.drawField();
    }

    public int getLevel() {
        return this.level.getLevel();
    }
}
