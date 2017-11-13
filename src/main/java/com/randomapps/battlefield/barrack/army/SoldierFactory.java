package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.SoldierType;

public class SoldierFactory {

    public static Soldier newInstance(SoldierType type) {
        if (type.equals(SoldierType.SERGEANT)) {
            return new Sergeant();
        }
        return null;
    }

}

