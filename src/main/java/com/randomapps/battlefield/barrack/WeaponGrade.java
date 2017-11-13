package com.randomapps.battlefield.barrack;

public enum WeaponGrade {

    ADVANCE (1), SIMPLE (3), COMPLEX(2);

    int grade;

    WeaponGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade(){
        return this.grade;
    }
}
