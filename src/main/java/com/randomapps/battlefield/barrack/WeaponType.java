package com.randomapps.battlefield.barrack;

public enum WeaponType {

    PISTOL("Pistol", 3, 3), BAZOOKA("Bazooka", 1, 2), SHOTGUN("Shotgun", 2, 2);

    String type;
    int grade;
    int availableRounds;

    WeaponType(String type, int grade, int availableRounds) {
        this.type = type;
        this.grade = grade;
        this.availableRounds = availableRounds;
    }

    public String getType(){
        return this.type;
    }

    public int getGrade() {
        return this.grade;
    }

    public int getAvailableRounds() {
        return availableRounds;
    }
}
