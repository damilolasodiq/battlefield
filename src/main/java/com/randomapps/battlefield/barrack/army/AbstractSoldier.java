package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.ArmorVest;
import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.exception.WeaponNotAssignableException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractSoldier implements Soldier {

    protected static final int DEFAULT_HEALTH = 100;
    protected Weapon currentWeapon;
    protected int health;
    private int[][] battleCoordinate;

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
        Optional<ArmorVest> armor = this.getArmorVest();
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
        Optional<ArmorVest> armor = this.getArmorVest();
        int weaponWeight = 100;
        if (armor.isPresent()) {
            if (armor.get().getHealth() > 0) {
                int reduce = armor.get().reduce(weaponWeight);
                if (reduce < 0)
                    this.health = this.health + reduce;
            } else {
                this.health = this.health - weaponWeight;
            }
        } else {
            this.health -= weaponWeight;
        }
    }

    @Override
    public abstract Optional<ArmorVest> getArmorVest();

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

    @Override
    public int[][] getBattleCoordinate() {
        return this.battleCoordinate;
    }

    @Override
    public abstract SoldierType getType();

    @Override
    public void setBattleCoordinate(int[][] battleCoordinate) {
        this.battleCoordinate = battleCoordinate;
    }
}
