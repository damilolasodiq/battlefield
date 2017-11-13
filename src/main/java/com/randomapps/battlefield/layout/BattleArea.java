package com.randomapps.battlefield.layout;

import com.randomapps.battlefield.game.Level;

import java.util.Arrays;
import java.util.function.Consumer;

public class BattleArea {

    private int row;
    private int col;
    private BattlePosition area[][];

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
/*
    void draw() {
        Consumer<Character[]> pipeDelimiter = (row) -> {
            Arrays.stream(row).forEach((el) -> {
                Character el1 = el == null ? '-' : el;
                System.out.print("| " + el1 + " ");
            });
            System.out.println("|");
        };

       *//* for (int i = 0; i < this.row; i++) {
            System.out.println("");
            for (int j = 0; j < this.col; j++) {
                area[i][j] = '-';
                System.out.print(area[i][j]);
            }
        }
        System.out.println("");*//*
        printMatrix(area, pipeDelimiter);

    }*/


}
