package main;

import entity.*;
import skill.PunchForWitch;

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
    public void setFire(){
        panel.fire[0] = new PunchForWitch(panel, 1, 20, panel.keyHandler);
        panel.fire[1] = new PunchForWitch(panel, 1, 20, panel.keyHandler);
        panel.fire[2] = new PunchForWitch(panel, 1, 20, panel.keyHandler);

        double angle = 0;
        double angleIncrement = 2 * Math.PI / 3;

        for (int i = 0; i < 3; i++) {
            panel.fire[i].angle = angle;
            angle += angleIncrement;
        }
    }
}