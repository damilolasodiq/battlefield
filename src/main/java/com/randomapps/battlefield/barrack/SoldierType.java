package com.randomapps.battlefield.barrack;

public enum SoldierType {

    CORPORAL("Corporal", 'C', 1), SERGEANT("Sergeant", 'S',2), GENERAL("General", 'G',3);
    char symbol;
    String name;
    int rank;

    SoldierType(String name, char symbol, int rank) {
        this.symbol = symbol;
        this.name = name;
        this.rank= rank;

    }

    public char getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return name;
    }

    public int getRank(){
        return this.rank;
    }
}
