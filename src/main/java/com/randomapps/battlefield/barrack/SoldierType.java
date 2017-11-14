package com.randomapps.battlefield.barrack;

public enum SoldierType {

    CORPORAL("Corporal", 'C'), SERGEANT("Sergeant", 'S'), GENERAL("General", 'G');
    char symbol;
    String name;

    SoldierType(String name, char symbol) {
        this.symbol = symbol;
        this.name = name;

    }

    public char getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return name;
    }
}
