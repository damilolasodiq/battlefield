package com.randomapps.battlefield;


import com.randomapps.battlefield.barrack.Arsenal;
import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.Bazooka;
import com.randomapps.battlefield.barrack.armory.Pistol;
import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.barrack.armory.WeaponFactory;
import com.randomapps.battlefield.barrack.army.Corporal;
import com.randomapps.battlefield.barrack.army.General;
import com.randomapps.battlefield.barrack.army.Sergeant;
import com.randomapps.battlefield.barrack.army.Soldier;
import com.randomapps.battlefield.exception.*;
import com.randomapps.battlefield.game.BattleFieldGame;
import com.randomapps.battlefield.game.Level;
import com.randomapps.battlefield.game.Player;
import com.randomapps.battlefield.game.SavedGame;
import com.randomapps.battlefield.layout.BattleField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class BattleFieldTest {

    String player1Name;
    String player2Name;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();



    @Before
    public void init() {
        player1Name = "Test Player 1";
        player2Name = "Test Player 2";
    }


    @Test
    public void gameInitializedCorrectly() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
         new BattleField(player1, player2, level);

        gamePlayerInitializedCorrectlyAssertions(player1, level, player1Name);
        gamePlayerInitializedCorrectlyAssertions(player2, level, player2Name);
        Assert.assertEquals("Both players must have the same number of soldiers", player1.getSoldiers().size(), player2.getSoldiers().size());


    }


    private void gamePlayerInitializedCorrectlyAssertions(Player player, Level level, String alias) {
        Assert.assertNotNull(String.format("%s must have Soldiers", alias), player.getSoldiers());
        Assert.assertEquals("Player name is not the same as the initial name", player.getName(), alias);
        Assert.assertEquals(String.format("%s must have the number of required soldier for level", alias), level.getSoldiers().size(), player.getSoldiers().size());
        Assert.assertNotNull(String.format("%s health cannot be less than 100%% for a new game", alias), player.getHealth() != 100);
    }

    @Test
    public void gameInitializedWithCPU() throws GameInitializationException {
        Player player = new Player(true);
        Assert.assertNotNull("Player must be a CPU", player.isCpu());
    }


    @Test(expected = GameInitializationException.class)
    public void initializeGameWithInvalidLevel() throws GameInitializationException {
        new Level(6);
    }

    @Test(expected = WeaponNotAssignableException.class)
    public void corporalPicksInvalidWeapon() throws WeaponNotAssignableException {
        Corporal corporal = new Corporal();
        corporal.assignWeapon(WeaponFactory.newInstance(WeaponType.BAZOOKA));
    }

    @Test(expected = WeaponNotAssignableException.class)
    public void sergeantPicksInvalidWeapon() throws WeaponNotAssignableException {
        Sergeant sergeant = new Sergeant();
        sergeant.assignWeapon(WeaponFactory.newInstance(WeaponType.BAZOOKA));
    }

    @Test
    public void corporalPicksValidWeaponAndWeaponIsAssigned() throws WeaponNotAssignableException {
        Corporal corporal = new Corporal();
        corporal.assignWeapon(WeaponFactory.newInstance(WeaponType.PISTOL));
        Assert.assertFalse(corporal.getArmorVest().isPresent());
        Assert.assertEquals(corporal.getCurrentWeapon().get().getWeaponType().getType(), WeaponFactory.newInstance(WeaponType.PISTOL).getWeaponType().getType());
    }

    @Test
    public void sergeantPicksValidWeaponAndWeaponIsAssigned() throws WeaponNotAssignableException {
        Sergeant sergeant = new Sergeant();
        sergeant.assignWeapon(WeaponFactory.newInstance(WeaponType.SHOTGUN));
        Assert.assertFalse(sergeant.getArmorVest().isPresent());
        Assert.assertEquals(sergeant.getCurrentWeapon().get().getWeaponType().getType(), WeaponFactory.newInstance(WeaponType.SHOTGUN).getWeaponType().getType());
    }

    @Test
    public void generalPicksValidWeaponAndWeaponIsAssigned() throws WeaponNotAssignableException {
        General general = new General();
        general.assignWeapon(WeaponFactory.newInstance(WeaponType.BAZOOKA));
        Assert.assertTrue(general.getArmorVest().isPresent());
        Assert.assertEquals(general.getCurrentWeapon().get().getWeaponType().getType(), WeaponFactory.newInstance(WeaponType.BAZOOKA).getWeaponType().getType());
    }


    @Test(expected = SoldierOutOfArmorException.class)
    public void soldierOutOfAmour() throws SoldierOutOfArmorException {
        Weapon pistol = new Pistol();
        while (pistol.availableRounds() > -1) {
            pistol.fire();
        }
    }

    @Test
    public void solderKillsOpponentSoldier() throws GameInitializationException, WeaponNotAssignableException {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Level level = new Level(2);
        BattleField battleField = new BattleField(player1, player2, level);

        Soldier soldier1 = player1.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().filter(s -> s.getSoldier().getType().equals(SoldierType.GENERAL)).findFirst().get().getSoldier();
        Soldier soldier2 = player2.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().filter(s -> s.getSoldier().getType().equals(SoldierType.CORPORAL)).findFirst().get().getSoldier();

        int points = player1.getStat().getPoints();
        battleField.attack(soldier1.getBattleCoordinate()[0][0], soldier1.getBattleCoordinate()[0][1], soldier2.getBattleCoordinate()[0][0], soldier2.getBattleCoordinate()[0][1]);

        Assert.assertEquals(1, player1.getStat().getNumberOfEnemiesKilled());
        Assert.assertEquals(1, player2.getStat().getNumberOfSoldiersKilled());
        Assert.assertTrue(points < player1.getStat().getPoints());
    }

    @Test
    public void solderHitGeneralsArmorVest() throws GameInitializationException, WeaponNotAssignableException {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Level level = new Level(2);
        BattleField battleField = new BattleField(player1, player2, level);


        Soldier soldier1 = player1.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().filter(s -> s.getSoldier().getType().equals(SoldierType.GENERAL)).findAny().get().getSoldier();
        Soldier soldier2 = player2.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().filter(s -> s.getSoldier().getType().equals(SoldierType.GENERAL)).findAny().get().getSoldier();

        Assert.assertEquals(100, soldier2.getArmorVest().get().getHealth());
        battleField.attack(soldier1.getBattleCoordinate()[0][0], soldier1.getBattleCoordinate()[0][1], soldier2.getBattleCoordinate()[0][0], soldier2.getBattleCoordinate()[0][1]);
        Assert.assertTrue(soldier2.getArmorVest().get().getHealth() < 100);
        Assert.assertEquals(1, player2.getStat().getNumberOfSoldierInjured());
    }

    @Test
    public void pauseAndResumeAGame() throws GameInitializationException, InvalidGameStateException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        BattleField battleFieldBeforePause = new BattleField(player1, player2, new Level(1));
        BattleFieldGame battleFieldGameBeforePause = new BattleFieldGame();
        battleFieldGameBeforePause.setBattleField(battleFieldBeforePause);

        battleFieldGameBeforePause.start();
        battleFieldBeforePause.attack(0, 0, 0, 0);
        battleFieldGameBeforePause.pause();

        BattleFieldGame battleFieldGameAfterPause = new BattleFieldGame();
        SavedGame<BattleField> battleFieldSavedGame = battleFieldGameAfterPause.lastSavedGame();
        Assert.assertNotNull("Could not get the last saved game", battleFieldBeforePause);
        Assert.assertNotNull("The retrieved game is null", battleFieldSavedGame.getGame());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        Assert.assertEquals(dateFormat.format(new Date()), dateFormat.format(battleFieldSavedGame.getDateSaved()));
        Assert.assertEquals("The Points for player 1 must be the same before and after pausing a game", battleFieldBeforePause.getCurrentPlayer().getStat().getPoints(), battleFieldBeforePause.getCurrentPlayer().getStat().getPoints());
        Assert.assertEquals("The Points for player 2 must be the same before and after pausing a game", battleFieldBeforePause.getOpponent().getStat().getPoints(), battleFieldBeforePause.getOpponent().getStat().getPoints());
    }

    @Test
    public void cpuPlayersTurnAndPlayerRemainsTheSame() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(true);
        Level level = new Level(1);
        BattleField battleField = new BattleField(player1, player2, level);
        battleField.attack(0,0,0,0);
        Assert.assertEquals(battleField.getCurrentPlayer(), player1);
        Assert.assertTrue(player2.isCpu());
    }


    @Test
    public void shouldAssignNewPistolToACorporal() throws GameInitializationException, WeaponNotAssignableException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        BattleField battleField = new BattleField(player1, player2, new Level(1));
        Soldier soldier = player1.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().filter(s -> s.getSoldier().getType().equals(SoldierType.CORPORAL)).findAny().get().getSoldier();
        Weapon OldWeapon = soldier.getCurrentWeapon().get();
        Arsenal arsenal = player1.getArsenal();

        Assert.assertNotNull(arsenal);
        Assert.assertNotNull(arsenal.getInventory());
        Assert.assertTrue(arsenal.hasWeapon(WeaponType.PISTOL));

        int beforeAssign = arsenal.getInventory().get(WeaponType.PISTOL).intValue();
        battleField.assignWeapon(soldier.getBattleCoordinate()[0][0], soldier.getBattleCoordinate()[0][1]);

        int afterAssign = arsenal.getInventory().getOrDefault(WeaponType.PISTOL, 0);
        Assert.assertEquals(beforeAssign - 1, afterAssign);
        Assert.assertNotNull(soldier.getCurrentWeapon());
        Assert.assertNotEquals(OldWeapon, soldier.getCurrentWeapon().get());

    }

    @Test(expected = InvalidGameStateException.class)
    public void shouldNotResumeAGameThatWasNotPaused() throws InvalidGameStateException, GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        BattleField battleFieldBeforePause = new BattleField(player1, player2, new Level(1));

        BattleFieldGame battleFieldGameBeforePause = new BattleFieldGame();
        battleFieldGameBeforePause.setBattleField(battleFieldBeforePause);
        battleFieldGameBeforePause.pause();

    }

    @Test
    public void shouldEndGameDueToDeadSoldiers() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        BattleField battleField = new BattleField(player1, player2, level);
        Soldier soldier = player1.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().findAny().get().getSoldier();
        player2.getSoldiers().stream().forEach(s -> {
            while (s.isAlive()) {
                s.takeHit(new Bazooka());
            }
        });
        battleField.attack(soldier.getBattleCoordinate()[0][0], soldier.getBattleCoordinate()[0][1], 0, 0);
        Assert.assertEquals(battleField.getWinner(), player1);
    }

    @Test
    public void shouldEndGameDueToNoWeapons() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        BattleField battleField = new BattleField(player1, player2, level);
        Soldier soldier = player1.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().findAny().get().getSoldier();
        player2.getSoldiers().stream().forEach(s -> {
            while (s.getCurrentWeapon().get().availableRounds() > 0) {
                try {
                    s.getCurrentWeapon().get().fire();
                } catch (SoldierOutOfArmorException e) {
                    e.printStackTrace();
                }
            }
        });
        battleField.attack(soldier.getBattleCoordinate()[0][0], soldier.getBattleCoordinate()[0][1], 0, 0);
        Assert.assertEquals(battleField.getWinner(), player1);
    }

    @Test
    public void shouldGoToNextLevelAfterCompletingALevel() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        BattleField battleField = new BattleField(player1, player2, level);
        Soldier soldier = player1.getBattleArea().getBattlePositionsWithActiveSoldiers().stream().findAny().get().getSoldier();
        player2.setSoldiers(new ArrayList<>());
        battleField.attack(soldier.getBattleCoordinate()[0][0], soldier.getBattleCoordinate()[0][1], 0, 0);
        BattleField newBattleField = battleField.goToNextLevel();
        Assert.assertEquals(battleField.getLevel() + 1, newBattleField.getLevel());
    }

    @Test(expected = GameInitializationException.class)
    public void shouldNotGoToANewLevelWhileCurrentLevelIsInPlay() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        BattleField battleField = new BattleField(player1, player2, level);
        battleField.goToNextLevel();
    }

    @Test
    public void shouldAddANewWeaponTypeToTheArsenal() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        new BattleField(player1, player2, level);
        Assert.assertFalse(player1.getArsenal().hasWeapon(WeaponType.BAZOOKA));
        player1.getArsenal().addWeapon(WeaponType.BAZOOKA);
        Assert.assertTrue(player1.getArsenal().hasWeapon(WeaponType.BAZOOKA));
    }

    @Test(expected = WeaponNotAvailableException.class)
    public void cannotPickAWeaponThatDoesNotExistFromArsenal() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        new BattleField(player1, player2, level);
        player1.getArsenal().pickWeapon(WeaponType.BAZOOKA);
    }



    @Test
    public void shouldStartGameAsConsoleWithTheCPU() throws GameInitializationException, IOException {
        systemInMock.provideLines("\n", "1", "Sodiq", "0000", "exit");
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(() -> {
            String[] split = systemOutRule.getLogWithNormalizedLineSeparator().split("\n");
            Assert.assertEquals(split[split.length - 1], BattleFieldGame.STOP_MESSAGE);
        });
        BattleFieldGameLauncher battleFieldGameLauncher = new BattleFieldGameLauncher();
        battleFieldGameLauncher.main(null);
    }

    @Test
    public void shouldStartGameAsConsoleWithTheASecondPlayer() throws GameInitializationException, IOException {
        systemInMock.provideLines("\n", "2", "P1", "P2", "0000", "exit");
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(() -> {
            String[] split = systemOutRule.getLogWithNormalizedLineSeparator().split("\n");
            Assert.assertEquals(split[split.length - 1], BattleFieldGame.STOP_MESSAGE);
        });
        BattleFieldGameLauncher battleFieldGameLauncher = new BattleFieldGameLauncher();
        battleFieldGameLauncher.main(null);
    }

    @Test
    public void shouldPauseTheGameFromTheConsole() throws GameInitializationException, IOException {
        systemInMock.provideLines("\n", "2", "P1", "P2", "0000", "pause");
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(() -> {
            String[] split = systemOutRule.getLogWithNormalizedLineSeparator().split("\n");
            Assert.assertEquals(split[split.length - 1], BattleFieldGame.PAUSE_MESSAGE);
        });
        BattleFieldGameLauncher battleFieldGameLauncher = new BattleFieldGameLauncher();
        battleFieldGameLauncher.main(null);
    }

}
