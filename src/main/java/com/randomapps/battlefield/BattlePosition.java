package com.randomapps.battlefield;

import com.randomapps.battlefield.barrack.army.Soldier;

import java.io.Serializable;

public class BattlePosition implements Serializable {
    Soldier soldier;

    boolean blasted;

    public BattlePosition() {
    }

    public BattlePosition(Soldier soldier) {
        this.soldier = soldier;

    }

    public boolean hasSoldier() {
        return this.soldier != null;
    }


    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }

    public boolean isBlasted() {
        return blasted;
    }

    public void setBlasted(boolean blasted) {
        this.blasted = blasted;
    }
}
