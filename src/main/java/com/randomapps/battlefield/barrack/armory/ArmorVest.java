package com.randomapps.battlefield.barrack.armory;

public class ArmorVest {

    private static final int DEFAULT_HEALTH = 100;
    private int health;

    public ArmorVest(int health) {
        this.health = health;
    }

    public ArmorVest() {
        this(DEFAULT_HEALTH);
    }


    public int getHealth(){
        return this.health;
    }

    public int reduce(int factor) {
        this.health -= factor;
        return this.health;
    }
}
