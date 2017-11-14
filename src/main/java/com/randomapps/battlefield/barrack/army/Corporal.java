package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.ArmorVest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Corporal extends AbstractSoldier {

    @Override
    public SoldierType getType() {
        return SoldierType.CORPORAL;
    }

    private static final List<WeaponType> ALLOWED_WEAPONS_TYPES = new ArrayList<>();
    private static final int RANK = 1;

    public Corporal() {
        super();
        this.health = 100;
        ALLOWED_WEAPONS_TYPES.addAll(Arrays.asList(WeaponType.PISTOL));
    }

    public List<WeaponType> allowedWeapons() {
        return ALLOWED_WEAPONS_TYPES;
    }

    @Override
    public int getRank() {
        return RANK;
    }

    @Override
    public Optional<ArmorVest> getArmorVest() {
        return Optional.empty();
    }
}
