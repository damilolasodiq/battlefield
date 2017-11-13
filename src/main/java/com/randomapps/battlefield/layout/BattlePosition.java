package com.randomapps.battlefield.layout;

import com.randomapps.battlefield.barrack.army.Soldier;

public class BattlePosition {

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
