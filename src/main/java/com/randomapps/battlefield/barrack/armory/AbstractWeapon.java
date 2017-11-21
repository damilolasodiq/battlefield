package com.randomapps.battlefield.barrack.armory;

import com.randomapps.battlefield.exception.SoldierOutOfArmorException;


public abstract class AbstractWeapon implements Weapon {

    protected int availableRounds;

    protected AbstractWeapon() {
        this.availableRounds = this.getWeaponType().getAvailableRounds();
    }

    @Override
    public final boolean isOutOfArmor() {
        return this.availableRounds <= 0;
    }


    @Override
    public final String name() {
        return this.getWeaponType().getType();
    }

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
