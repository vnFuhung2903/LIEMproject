package main;

import entity.*;
public class AssetSetter {

    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setMonster() {

        panel.monster[0] = new Monster(panel, 1, 20);
        panel.monster[0].setPosX(panel.tileSize*10);
        panel.monster[0].setPosY(panel.tileSize*13);

        panel.monster[1] = new Monster(panel, 1, 20);
        panel.monster[1].setPosX(panel.tileSize*12);
        panel.monster[1].setPosY(panel.tileSize*14);

        panel.monster[2] = new Monster(panel, 1, 20);
        panel.monster[2].setPosX(panel.tileSize*13);
        panel.monster[2].setPosY(panel.tileSize*15);

        panel.monster[3] = new Monster(panel, 1, 20);
        panel.monster[3].setPosX(panel.tileSize*15);
        panel.monster[3].setPosY(panel.tileSize*17);

    }
}