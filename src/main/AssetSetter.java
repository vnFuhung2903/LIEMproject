package main;

import entity.*;
import skill.PunchForWitch;

public class AssetSetter {

    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setMonster() {

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