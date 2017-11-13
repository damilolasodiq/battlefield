package com.randomapps.battlefield.barrack.armory;

public class Armor {

    private static final int DEFAULT_HEALTH = 100;
    private int health;

    public Armor(int health) {
        this.health = health;
    }

    public Armor() {
        this.health = DEFAULT_HEALTH;
    }


    public int getHealth(){
        return this.health;
    }

    public int reduce(int factor) {
        this.health -= factor;
        return this.health;
    }
}
