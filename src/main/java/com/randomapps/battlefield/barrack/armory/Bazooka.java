package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;

public class Bazooka extends AbstractWeapon {
    private static final int AVAILABLE_ROUNDS = 2;
    private static final int TRAVEL_RANGE = 10;
    private static final int SCORE_POINT = 15;

    public Bazooka() {
        this.availableRounds = AVAILABLE_ROUNDS;
    }


    @Override
    public WeaponType getWeaponType() {
        return WeaponType.BAZOOKA;
    }

    @Override
    public int scorePoint() {
        return SCORE_POINT;
    }

    @Override
    public String name() {
        return WeaponType.BAZOOKA.getType();
    }

}
