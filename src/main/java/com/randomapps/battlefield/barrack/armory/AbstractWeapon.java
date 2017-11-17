package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.exception.SoldierOutOfArmorException;


public abstract class AbstractWeapon implements Weapon {

    protected int availableRounds;


    @Override
    public final boolean isOutOfArmor() {
        return this.availableRounds <= 0;
    }

    @Override
    public abstract WeaponType getWeaponType();

    @Override
    public abstract int scorePoint();

    @Override
    public abstract String name();

    @Override
    public int availableRounds() {
        return this.availableRounds;
    }

    @Override
    public final void fire() throws SoldierOutOfArmorException {
        if (this.isOutOfArmor())
            throw new SoldierOutOfArmorException("This weapon is out of ArmorVest");
        this.availableRounds--;
    }

}
