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
    private int level;
    private int numberOfSoldiersRequired;
    private int numberOfGeneralsRequired;
    private int numberOfCorporalsRequired;
    private int numberOfSergeantsRequired;
    private int maxHealthInLevel;

    private List<Soldier> soldiers;
    private List<WeaponType> weaponTypes;

    public Level(int level) throws GameInitializationException {
        if (level > MAX_LEVEL || level < 1) {
            throw new GameInitializationException("Invalid game level selected");
        }
        this.level = level;
        this.row = DEFAULT_ROW_SIZE + level - 1;
        this.column = DEFAULT_COLUMN_SIZE + level - 1;
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
        this.numberOfSoldiersRequired = ((row * column) / 2) - level;
        this.numberOfGeneralsRequired = (level * 2) - 2;
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
        if (this.numberOfCorporalsRequired > 0) {
            int numberOfRequiredPistols = this.numberOfCorporalsRequired + (int) Math.floor(this.numberOfCorporalsRequired / 2) - 1;
            weaponTypes.addAll(Collections.nCopies(numberOfRequiredPistols, WeaponType.PISTOL));
        }
        if (this.numberOfGeneralsRequired > 0) {
            int numberOfRequiredBazookas = this.numberOfGeneralsRequired + (int) Math.floor(this.numberOfGeneralsRequired / 2) - 1;
            weaponTypes.addAll(Collections.nCopies(numberOfRequiredBazookas, WeaponType.BAZOOKA));
        }
        if (this.numberOfSergeantsRequired > 0) {
            int numberOfShotguns = this.numberOfSergeantsRequired + (int) Math.floor(this.numberOfSergeantsRequired / 2) - 1;
            weaponTypes.addAll(Collections.nCopies(numberOfShotguns, WeaponType.SHOTGUN));
        }
    }

    public List<WeaponType> getWeaponTypes() {
        return this.weaponTypes;
    }

    private void initHealth() {
        this.maxHealthInLevel = this.getSoldiers().stream().mapToInt(soldier -> soldier.getHealth()).sum();
    }

    public int getLevel() {
        return this.level;
    }

    public int getMaxHealthInLevel() {
        return this.maxHealthInLevel;
    }

}
