package com.randomapps.battlefield.game;

import com.randomapps.battlefield.barrack.Arsenal;
import com.randomapps.battlefield.barrack.army.Soldier;
import com.randomapps.battlefield.layout.BattleArea;
import com.randomapps.battlefield.util.GameHelper;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable{

    private final String name;
    private final boolean cpu;
    private List<Soldier> soldiers;
    private Arsenal arsenal;
    private BattleArea battleArea;
    private PlayerStat stat;

    public Player(String name) {
        this.name = name;
        this.cpu = false;
    }

    public Player(boolean cpu) {
        this.name = String.format("%s (CPU)",GameHelper.getCPUName());
        this.cpu = cpu;
    }

    public boolean isCpu() {
        return cpu;
    }

    public int getHealth() {
        if (soldiers == null)
            return 0;
        return soldiers.stream().filter(Soldier::isAlive).mapToInt(Soldier::getHealth).sum();
    }

    public long getNumberOfInjuredSoldiers() {
        return soldiers.stream().filter(soldier -> soldier.getHealth() > 0 && soldier.getHealth() < 100).count();
    }

    public long getNumberOfDeadSoldiers() {
        return soldiers.stream().filter(soldier -> !soldier.isAlive()).count();
    }


    public String getName() {
        return name;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(List<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public Arsenal getArsenal() {
        return arsenal;
    }

    public void setArsenal(Arsenal arsenal) {
        this.arsenal = arsenal;
    }

    public PlayerStat getStat() {
        return stat;
    }

    public void setStat(PlayerStat stat) {
        this.stat = stat;
    }

    public BattleArea getBattleArea() {
        return battleArea;
    }

    public void setBattleArea(BattleArea battleArea) {
        this.battleArea = battleArea;
    }

}
