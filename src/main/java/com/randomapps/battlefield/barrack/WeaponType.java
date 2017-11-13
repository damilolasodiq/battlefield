package com.randomapps.battlefield.barrack;

public enum WeaponType {

    PISTOL("Pistol"), BAZOOKA("Bazooka"), SHOTGUN("Shotgun");

    String type;

    WeaponType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
