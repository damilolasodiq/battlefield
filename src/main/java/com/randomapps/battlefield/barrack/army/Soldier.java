package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.Armor;
import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.exception.WeaponNotAssignableException;

import java.util.List;
import java.util.Optional;

public interface Soldier extends Cloneable {

    List<WeaponType> allowedWeapons();

    int getHealth();

    Optional<Weapon> getCurrentWeapon();

    boolean isAlive();

    Character getSymbol();

    Optional<Armor> getArmor();

    boolean canUseWeapon(WeaponType weaponType);

    void assignWeapon(Weapon weapon) throws WeaponNotAssignableException;

    void takeHit(Weapon weapon);

    Soldier clone();

    int getRank();
}
