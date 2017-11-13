package com.randomapps.battlefield.layout;


import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.game.Level;
import com.randomapps.battlefield.game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BattleFieldTest {

    String player1Name;
    String player2Name;

    @Before
    public void init(){
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

}
