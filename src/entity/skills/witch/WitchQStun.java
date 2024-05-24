package entity.skills.witch;

import entity.Effect;
import entity.Monster;
import main.Panel;

import java.awt.*;

public class WitchQStun {
    Panel panel;
    Monster monster;
    int effectTime, posX, posY, tick, index;
    boolean active;

    public WitchQStun(Panel panel, Monster monster, int time) {
        this.panel = panel;
        this.monster = monster;
        this.effectTime = time;
        active = true;
    }

    public void update() {
        posX = monster.getPosX();
        posY = monster.getPosY();
        monster.setStun();
        if (++tick >= 20) {
            tick = 0;
            if (++index >= 4) {
                index = 0;
                if(--effectTime == 0)  {
                    active = false;
                    monster.setNotStun();
                    effectTime = 5;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if(monster.onScreen(monster.getMonsterSize(), screenX, screenY))
            panel.getAsset().getWitchQStunAssets().draw(screenX, screenY, index, monster.getMonsterSize(), g2);

    }

    public boolean isActive() {
        return active;
    }

    public Monster getMonster() {
        return monster;
    }
}
