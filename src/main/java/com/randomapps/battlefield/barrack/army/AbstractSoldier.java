package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.Armor;
import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.exception.WeaponNotAssignableException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractSoldier implements Soldier {

    protected static final int DEFAULT_HEALTH = 100;
    protected Weapon currentWeapon;
    protected int health;

    AbstractSoldier() {
        this.health = DEFAULT_HEALTH;
    }

    public abstract List<WeaponType> allowedWeapons();

    @Override
    public Optional<Weapon> getCurrentWeapon() {
        return Optional.ofNullable(this.currentWeapon);
    }

    @Override
    public final int getHealth() {
        Optional<Armor> armor = this.getArmor();
        if (armor.isPresent()) {
            return this.health + armor.get().getHealth();
        }
        return this.health;
    }


    @Override
    public final boolean canUseWeapon(WeaponType weaponType) {
        return this.allowedWeapons().contains(weaponType);
    }

    @Override
    public final void assignWeapon(Weapon weapon) throws WeaponNotAssignableException {
        if (this.canUseWeapon(weapon.getWeaponType())) {
            this.currentWeapon = weapon;
        }
    }


    @Override
    public void takeHit(Weapon weapon) {
        Optional<Armor> armor = this.getArmor();
        int weaponWeight = weapon.travelRange();
        if (armor.isPresent()) {
            if (armor.get().getHealth() > 0) {
                int reduce = armor.get().reduce(weaponWeight);
                if (reduce < 0)
                    this.health = this.health + reduce;
            } else {
                this.health = this.health - weaponWeight;
            }
        }
    }

    @Override
    public abstract Optional<Armor> getArmor();

    @Override
    public abstract Character getSymbol();

    public final boolean isAlive() {
        return this.getHealth() > 0;

    }

    public Soldier clone() {
        try {
            return (Soldier) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public abstract int getRank();
}
