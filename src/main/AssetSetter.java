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

    }
}