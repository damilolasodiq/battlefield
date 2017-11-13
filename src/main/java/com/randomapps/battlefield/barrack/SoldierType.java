package com.randomapps.battlefield.barrack;

public enum SoldierType {

    CORPORAL("corporal"), SERGEANT("sergeant"), GENERAL("general");
    String type;

    SoldierType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
