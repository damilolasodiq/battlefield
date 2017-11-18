package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;

public class Bazooka extends AbstractWeapon {
    private static final int SCORE_POINT = 15;

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.BAZOOKA;
    }

    @Override
    public int scorePoint() {
        return SCORE_POINT;
    }

}
