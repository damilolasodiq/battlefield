package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.exception.WeaponNotAvailableException;

public class WeaponFactory {
    public static Weapon newInstance(WeaponType type) {
        if (type.equals(WeaponType.PISTOL)) {
            return new Pistol();
        } else if (type.equals(WeaponType.BAZOOKA)) {
            return new Bazooka();
        } else if (type.equals(WeaponType.SHOTGUN)) {
            return new ShotGun();
        }
        throw new WeaponNotAvailableException("An unknown weapon type was selected");
    }

}
