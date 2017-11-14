package com.randomapps.battlefield.layout;

import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.game.Level;
import com.randomapps.battlefield.game.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class OutputStreamTests {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    String player1Name;
    String player2Name;

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        player1Name = "Test Player 1";
        player2Name = "Test Player 2";
    }

    @After
    public void destroy() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void checkStatusCanPrintToStream() throws GameInitializationException {
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Level level = new Level(1);
        BattleField battleField = new BattleField(player1, player2, level);

        battleField.printStat(player1, player2);
        Assert.assertNotNull(outContent.toString());
        Assert.assertTrue(errContent.size() == 0);
    }
}
