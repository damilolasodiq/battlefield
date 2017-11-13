package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;

public class Pistol extends AbstractWeapon {

    private static final int AVAILABLE_ROUNDS = 3;
    private static final int TRAVEL_RANGE = 2;
    private static final int SCORE_POINT = 10;

    public Pistol() {
        this.availableRounds = AVAILABLE_ROUNDS;
    }


    @Override
    public String name() {
        return this.getWeaponType().name();
    }

    @Override
    public int travelRange() {
        return TRAVEL_RANGE;
    }

    @Override
    public int scorePoint() {
        return SCORE_POINT;
    }


    @Override
    public WeaponType getWeaponType() {
        return WeaponType.PISTOL;
    }

}
