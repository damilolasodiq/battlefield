package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.exception.SoldierOutOfArmorException;

import java.io.Serializable;

public interface Weapon extends Serializable {

    String name();

    int availableRounds();

    void fire() throws SoldierOutOfArmorException;

    boolean isOutOfArmor();

    WeaponType getWeaponType();

    int scorePoint();
}
