package com.randomapps.battlefield.game;

public class PlayerStat {


    private int numberOfSoldiersKilled;
    private int numberOfSoldierInjured;
    private int numberOfEnemiesKilled;
    private int points;

    public int getNumberOfSoldiersKilled() {
        return numberOfSoldiersKilled;
    }

    public void setNumberOfSoldiersKilled(int numberOfSoldiersKilled) {
        this.numberOfSoldiersKilled = numberOfSoldiersKilled;
    }

    public int getNumberOfSoldierInjured() {
        return numberOfSoldierInjured;
    }

    public void setNumberOfSoldierInjured(int numberOfSoldierInjured) {
        this.numberOfSoldierInjured = numberOfSoldierInjured;
    }

    public int getNumberOfEnemiesKilled() {
        return numberOfEnemiesKilled;
    }

    public void setNumberOfEnemiesKilled(int numberOfEnemiesKilled) {
        this.numberOfEnemiesKilled = numberOfEnemiesKilled;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
