package main;

import entity.*;
public class AssetSetter {

    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setMonster() {

        panel.monsters[0] = new Monster(panel, 1, 20);
        panel.monsters[0].setPosX(panel.tileSize*10);
        panel.monsters[0].setPosY(panel.tileSize*13);

        panel.monsters[1] = new Monster(panel, 1, 20);
        panel.monsters[1].setPosX(panel.tileSize*12);
        panel.monsters[1].setPosY(panel.tileSize*14);

        panel.monsters[2] = new Monster(panel, 1, 20);
        panel.monsters[2].setPosX(panel.tileSize*13);
        panel.monsters[2].setPosY(panel.tileSize*15);

        panel.monsters[3] = new Monster(panel, 1, 20);
        panel.monsters[3].setPosX(panel.tileSize*15);
        panel.monsters[3].setPosY(panel.tileSize*17);

    }
}