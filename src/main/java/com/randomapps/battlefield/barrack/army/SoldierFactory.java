package com.randomapps.battlefield.barrack.army;

import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.exception.UnknownSoldierTypeException;

public class SoldierFactory {

    public static Soldier newInstance(SoldierType type) throws UnknownSoldierTypeException{
        if (SoldierType.SERGEANT.equals(type)) {
            return new Sergeant();
        }if (SoldierType.CORPORAL.equals(type)) {
            return new Corporal();
        }if (SoldierType.GENERAL.equals(type)) {
            return new General();
        }
        throw new UnknownSoldierTypeException("The Type of Solder is not known");
    }

}

