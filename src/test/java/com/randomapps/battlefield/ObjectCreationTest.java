package com.randomapps.battlefield;

import com.randomapps.battlefield.barrack.SoldierType;
import com.randomapps.battlefield.barrack.WeaponType;
import com.randomapps.battlefield.barrack.armory.*;
import com.randomapps.battlefield.barrack.army.*;
import com.randomapps.battlefield.exception.UnknownSoldierTypeException;
import com.randomapps.battlefield.exception.WeaponNotAvailableException;
import org.junit.Assert;
import org.junit.Test;

public class ObjectCreationTest {

    @Test
    public void shouldCreateACorporalType() throws UnknownSoldierTypeException {
        Soldier soldier = SoldierFactory.newInstance(SoldierType.CORPORAL);
        Assert.assertTrue(soldier instanceof Corporal);
    }

    @Test
    public void shouldCreateASergeantType() throws UnknownSoldierTypeException {
        Soldier soldier = SoldierFactory.newInstance(SoldierType.SERGEANT);
        Assert.assertTrue(soldier instanceof Sergeant);
    }

    @Test
    public void shouldCreateAGeneralType() throws UnknownSoldierTypeException {
        Soldier soldier = SoldierFactory.newInstance(SoldierType.GENERAL);
        Assert.assertTrue(soldier instanceof General);
    }

    @Test
    public void shouldCreateABazooka() {
        Weapon weapon = WeaponFactory.newInstance(WeaponType.BAZOOKA);
        Assert.assertTrue(weapon instanceof Bazooka);
    }

    @Test
    public void shouldCreateAPistol() {
        Weapon weapon = WeaponFactory.newInstance(WeaponType.PISTOL);
        Assert.assertTrue(weapon instanceof Pistol);
    }

    @Test
    public void shouldCreateAShotgun() {
        Weapon weapon = WeaponFactory.newInstance(WeaponType.SHOTGUN);
        Assert.assertTrue(weapon instanceof ShotGun);
    }

    @Test(expected = WeaponNotAvailableException.class)
    public void shouldNotCreateAWeapon() {
        WeaponFactory.newInstance(null);
    }

    @Test(expected = UnknownSoldierTypeException.class)
    public void shouldNotCreateASoldier() {
        SoldierFactory.newInstance(null);
    }
}
