package main;

import entity.*;
public class AssetSetter {

    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setMonster() {

        panel.monster[0] = new Monster(panel, 1, 20);
        panel.monster[0].posX = panel.tileSize*10;
        panel.monster[0].posY = panel.tileSize*13;

        panel.monster[1] = new Monster(panel, 1, 20);
        panel.monster[1].posX = panel.tileSize*12;
        panel.monster[1].posY = panel.tileSize*14;

        panel.monster[2] = new Monster(panel, 1, 20);
        panel.monster[2].posX = panel.tileSize*13;
        panel.monster[2].posY = panel.tileSize*15;

        panel.monster[3] = new Monster(panel, 1, 20);
        panel.monster[3].posX = panel.tileSize*15;
        panel.monster[3].posY = panel.tileSize*17;

    }
}