package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.Armor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Corporal extends AbstractSoldier {
    private static final List<WeaponType> ALLOWED_WEAPONS_TYPES = new ArrayList<>();
    private static final Character SYMBOL = 'C';
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
    public Character getSymbol() {
        return SYMBOL;
    }

    @Override
    public int getRank() {
        return RANK;
    }

    @Override
    public Optional<Armor> getArmor() {
        return Optional.empty();
    }
}
