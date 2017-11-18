package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;

public class Pistol extends AbstractWeapon {

    private static final int SCORE_POINT = 10;

    @Override
    public int scorePoint() {
        return SCORE_POINT;
    }


    @Override
    public WeaponType getWeaponType() {
        return WeaponType.PISTOL;
    }

}
