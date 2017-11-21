package com.randomapps.battlefield.barrack.armory;

import java.io.Serializable;

public class ArmorVest implements Serializable {

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
