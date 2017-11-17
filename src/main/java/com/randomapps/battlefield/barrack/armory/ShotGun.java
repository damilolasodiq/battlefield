package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;

public class ShotGun extends AbstractWeapon {

    private static final int AVAILABLE_ROUNDS = 2;
    private static final int TRAVEL_RANGE = 2;
    private static final int SCORE_POINT = 12;

    public ShotGun() {
        this.availableRounds = AVAILABLE_ROUNDS;
    }

    @Override
    public final String name() {
        return this.getWeaponType().getType();
    }

    @Override
    public int scorePoint() {
        return SCORE_POINT;
    }

    @Override
    public final WeaponType getWeaponType() {
        return WeaponType.SHOTGUN;
    }


}
