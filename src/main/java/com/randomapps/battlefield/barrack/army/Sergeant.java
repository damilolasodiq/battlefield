package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.Armor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Sergeant extends AbstractSoldier {

    private static final List<WeaponType> ALLOWED_WEAPONS_TYPES = new ArrayList<>();
    private static final Character SYMBOL = 'S';
    private static final int RANK = 2 ;

    public Sergeant() {
        super();
        //this.health = 120;
        ALLOWED_WEAPONS_TYPES.addAll(Arrays.asList(WeaponType.PISTOL, WeaponType.SHOTGUN));
    }

    public List<WeaponType> allowedWeapons() {
        return ALLOWED_WEAPONS_TYPES;
    }

    @Override
    public Character getSymbol() {
        return SYMBOL;
    }

    @Override
    public Optional<Armor> getArmor() {
        return Optional.empty();
    }

    @Override
    public int getRank() {
        return RANK;
    }

}
