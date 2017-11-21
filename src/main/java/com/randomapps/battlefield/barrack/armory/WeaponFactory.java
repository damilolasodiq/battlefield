package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.exception.WeaponNotAvailableException;

public class WeaponFactory {

    private WeaponFactory() {
    }

    public static Weapon newInstance(WeaponType type) {
        if (WeaponType.PISTOL.equals(type)) {
            return new Pistol();
        } else if (WeaponType.BAZOOKA.equals(type)) {
            return new Bazooka();
        } else if (WeaponType.SHOTGUN.equals(type)) {
            return new ShotGun();
        }
        throw new WeaponNotAvailableException("An unknown weapon type was selected");
    }

}
