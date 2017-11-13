package com.randomapps.battlefield.layout;

import com.randomapps.battlefield.game.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BattleArea {

    private int row;
    private int col;
    private BattlePosition[][] area;

    BattleArea(Level level) {
        this.row = level.getRow();
        this.col = level.getColumn();
        this.area = new BattlePosition[row][col];
        // this.draw();
    }

    private static void printMatrix(Character[][] matrix, Consumer<Character[]> rowPrinter) {
        Arrays.stream(matrix)
                .forEach((row) -> rowPrinter.accept(row));
    }

    public BattlePosition[][] getArea() {
        return this.area;
    }

    public List<BattlePosition> getBattlePositionsWithActiveSoldiers() {
        List<BattlePosition> areasWithSoldiers = new ArrayList<>();
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                BattlePosition battlePosition = area[i][j];
                if (battlePosition != null && !battlePosition.isBlasted() && battlePosition.hasSoldier() && battlePosition.getSoldier().isAlive())
                    areasWithSoldiers.add(battlePosition);
            }
        }
        return areasWithSoldiers;
    }
}
