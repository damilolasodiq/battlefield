package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.ArmorVest;
import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.exception.WeaponNotAssignableException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Soldier extends Serializable {

    List<WeaponType> allowedWeapons();

    int getHealth();

    Optional<Weapon> getCurrentWeapon();

    boolean isAlive();

    Optional<ArmorVest> getArmorVest();

    boolean canUseWeapon(WeaponType weaponType);

    void assignWeapon(Weapon weapon) throws WeaponNotAssignableException;

    void takeHit(Weapon weapon);

    int getRank();

    int[][] getBattleCoordinate();

    SoldierType getType();

    void setBattleCoordinate(int[][] battleCoordinate);
}
