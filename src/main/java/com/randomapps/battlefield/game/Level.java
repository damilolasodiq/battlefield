package com.randomapps.battlefield.game;

import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.army.Corporal;
import com.randomapps.battlefield.barrack.army.General;
import com.randomapps.battlefield.barrack.army.Sergeant;
import com.randomapps.battlefield.barrack.army.Soldier;
import com.randomapps.battlefield.exception.GameInitializationException;
import com.randomapps.battlefield.util.CollectionsHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level implements Serializable {

    private static final int DEFAULT_ROW_SIZE = 4;
    private static final int DEFAULT_COLUMN_SIZE = 6;
    public static final int MAX_LEVEL = 5;

    private int row;
    private int column;
    private int currentLevel;

    private int numberOfGeneralsRequired;
    private int numberOfCorporalsRequired;
    private int numberOfSergeantsRequired;
    private int maxHealthInLevel;

    private List<Soldier> soldiers;
    private List<WeaponType> weaponTypes;

    public Level(int currentLevel) throws GameInitializationException {
        if (currentLevel > MAX_LEVEL || currentLevel < 1) {
            throw new GameInitializationException("Invalid game currentLevel selected");
        }
        this.currentLevel = currentLevel;
        this.row = DEFAULT_ROW_SIZE + currentLevel - 1;
        this.column = DEFAULT_COLUMN_SIZE + currentLevel - 1;
        this.initSoldiers();
        this.initWeaponTypes();
        this.initHealth();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public List<Soldier> getSoldiers() {
        return this.soldiers;

    }

    private void initSoldiers() {
        int numberOfSoldiersRequired = ((row * column) / 2) - currentLevel;
        this.numberOfGeneralsRequired = (currentLevel * 2) - 2;
        if (this.numberOfGeneralsRequired < 2) {
            this.numberOfSergeantsRequired = 2;
        } else {
            this.numberOfSergeantsRequired = numberOfGeneralsRequired - 2;
        }
        this.numberOfCorporalsRequired = numberOfSoldiersRequired - (numberOfGeneralsRequired + numberOfSergeantsRequired);

        this.soldiers = new ArrayList<>();
        if (this.numberOfCorporalsRequired > 0) {
            soldiers.addAll(CollectionsHelper.createListWithNewObjects(Corporal.class, this.numberOfCorporalsRequired));
        }
        if (this.numberOfSergeantsRequired > 0) {
            soldiers.addAll(CollectionsHelper.createListWithNewObjects(Sergeant.class, this.numberOfSergeantsRequired));
        }
        if (this.numberOfGeneralsRequired > 0) {
            soldiers.addAll(CollectionsHelper.createListWithNewObjects(General.class, this.numberOfGeneralsRequired));
        }
    }

    private void initWeaponTypes() {
        this.weaponTypes = new ArrayList<>();
        if (this.numberOfSergeantsRequired > 0) {
            int numberOfShotguns = this.numberOfSergeantsRequired + (this.numberOfSergeantsRequired / 2) - 1;
            weaponTypes.addAll(Collections.nCopies(numberOfShotguns, WeaponType.SHOTGUN));
        }
        if (this.numberOfGeneralsRequired > 0) {
            int numberOfRequiredBazookas = this.numberOfGeneralsRequired + (this.numberOfGeneralsRequired / 2) - 1;
            weaponTypes.addAll(Collections.nCopies(numberOfRequiredBazookas, WeaponType.BAZOOKA));
        }
        if (this.numberOfCorporalsRequired > 0) {
            int numberOfRequiredPistols = this.numberOfCorporalsRequired + (this.numberOfCorporalsRequired / 2) - 1;
            weaponTypes.addAll(Collections.nCopies(numberOfRequiredPistols, WeaponType.PISTOL));
        }
    }

    public List<WeaponType> getWeaponTypes() {
        return this.weaponTypes;
    }

    private void initHealth() {
        this.maxHealthInLevel = this.getSoldiers().stream().mapToInt(Soldier::getHealth).sum();
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getMaxHealthInLevel() {
        return this.maxHealthInLevel;
    }

}
