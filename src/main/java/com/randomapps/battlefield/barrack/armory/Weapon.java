package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.exception.SoldierOutOfArmorException;

public interface Weapon {

    String name();

    int availableRounds();

    void fire() throws SoldierOutOfArmorException;

    int travelRange();

    boolean isOutOfArmor();

    WeaponType getWeaponType();

    int scorePoint();
}
