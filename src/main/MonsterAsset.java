package main;

import assets.*;


public class MonsterAsset {
    Panel panel;
    Hobgoblin hobgoblinAsset;
    Goblin goblinAssets;
    Slime slimeAssets;
    Spider spiderAssets;
    Slave slaveAssets;
    Ghost ghostAssets;
    Skeleton skeletonAssets;
    WitchE witchEAssets;

    public MonsterAsset(Panel panel) {
        this.panel = panel;
        hobgoblinAsset = new Hobgoblin(panel);
        goblinAssets = new Goblin(panel);
        slimeAssets = new Slime(panel);
        spiderAssets = new Spider(panel);
        slaveAssets = new Slave(panel);
        ghostAssets = new Ghost(panel);
        skeletonAssets = new Skeleton(panel);
        witchEAssets = new WitchE(panel);
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
    public Ghost getGhostAssets() {return ghostAssets;}
    public Skeleton getSkeletonAssets() {return skeletonAssets;}

    public WitchE getWitchEAssets() {
        return witchEAssets;
    }
}
