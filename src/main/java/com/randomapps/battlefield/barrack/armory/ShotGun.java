package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;

public class ShotGun extends AbstractWeapon {

    private static final int SCORE_POINT = 12;

    @Override
    public int scorePoint() {
        return SCORE_POINT;
    }

    @Override
    public final WeaponType getWeaponType() {
        return WeaponType.SHOTGUN;
    }

}
