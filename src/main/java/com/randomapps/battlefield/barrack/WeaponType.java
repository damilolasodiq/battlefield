package com.randomapps.battlefield.barrack;

public enum WeaponType {

    PISTOL("Pistol", 3), BAZOOKA("Bazooka", 1), SHOTGUN("Shotgun", 2);

    String type;
    int grade;

    WeaponType(String type, int grade) {
        this.type = type;
        this.grade = grade;
    }

    public String getType(){
        return this.type;
    }

    public int getGrade() {
        return this.grade;
    }
}
