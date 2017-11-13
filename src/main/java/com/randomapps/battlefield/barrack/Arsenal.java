package com.randomapps.battlefield.barrack;

import com.randomapps.battlefield.barrack.armory.Weapon;
import com.randomapps.battlefield.barrack.armory.WeaponFactory;
import com.randomapps.battlefield.exception.WeaponNotAvailableException;

import java.util.HashMap;
import java.util.Map;

public class Arsenal {

    private Map<WeaponType, Integer> inventory = new HashMap();

    public void addWeapon(WeaponType weaponType) {
        this.addWeapon(weaponType, 1);
    }

    public final void addWeapon(WeaponType weaponType, int count) {
        if (count > 0) {
            if (this.inventory.containsKey(weaponType)) {
                int weaponCount = this.inventory.get(weaponType);
                this.inventory.put(weaponType, count + weaponCount);
            } else {
                this.inventory.put(weaponType, count);
            }
        }
    }

    public final Weapon pickWeapon(WeaponType weaponType) throws WeaponNotAvailableException {
        if (this.hasWeapon(weaponType)) {
            int weaponCount = this.inventory.get(weaponType);
            if (weaponCount <= 1) {
                this.inventory.remove(weaponType);
            } else {
                this.inventory.put(weaponType, --weaponCount);
            }
            return WeaponFactory.newInstance(weaponType);

        } else {
            throw new WeaponNotAvailableException("");
        }
    }

    public final boolean hasWeapon(WeaponType weaponType) {
        return this.inventory.containsKey(weaponType);
    }

    public Map<WeaponType, Integer> getInventory() {
        Map<WeaponType, Integer> inventory = new HashMap<>();
        this.inventory.forEach((k, v) -> {
            inventory.put(k, v);
        });
        return inventory;
    }

    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }
}
