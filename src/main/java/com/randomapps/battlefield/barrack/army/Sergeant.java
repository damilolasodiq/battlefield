package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.ArmorVest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Sergeant extends AbstractSoldier {

    private static final List<WeaponType> ALLOWED_WEAPONS_TYPES = new ArrayList<>();

    public Sergeant() {
        super();
        ALLOWED_WEAPONS_TYPES.addAll(Arrays.asList(WeaponType.PISTOL, WeaponType.SHOTGUN));
    }

    public List<WeaponType> allowedWeapons() {
        return ALLOWED_WEAPONS_TYPES;
    }

    @Override
    public Optional<ArmorVest> getArmorVest() {
        return Optional.empty();
    }


    @Override
    public SoldierType getType() {
        return SoldierType.SERGEANT;
    }
}
