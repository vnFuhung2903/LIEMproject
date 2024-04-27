package main;

import assets.*;

public class MonsterAsset {
    Panel panel;
    Hobgoblin hobgoblinAsset;
    Goblin goblinAssets;
    Slime slimeAssets;
    Spider spiderAssets;
    Slave slaveAssets;

    public MonsterAsset(Panel panel) {
        this.panel = panel;
        hobgoblinAsset = new Hobgoblin(panel);
        goblinAssets = new Goblin(panel);
        slimeAssets = new Slime(panel);
        spiderAssets = new Spider(panel);
        slaveAssets = new Slave(panel);
    }

    public Hobgoblin getHobgoblinAsset() {
        return hobgoblinAsset;
    }

    public Goblin getGoblinAssets() {
        return goblinAssets;
    }

    public Slime getSlimeAssets() {
        return slimeAssets;
    }

    public Spider getSpiderAssets() {
        return spiderAssets;
    }

    public Slave getSlaveAssets() {
        return slaveAssets;
    }
}
