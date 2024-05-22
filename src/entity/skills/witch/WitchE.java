package entity.skills.witch;

import entity.Entity;
import entity.Monster;
import entity.Skill;
import main.Panel;

import java.awt.*;

public class WitchE extends Skill {

    Monster monster;
    public WitchE(Panel panel, int speed, Entity entity, Monster monster) {
        super(panel, speed, 10, entity);
        casted = true;
        spriteIndex = 0;
        this.monster = monster;
        this.posX = entity.getPosX();
        this.posY = entity.getPosY();
        System.out.println("Creating a explosion at " + posX + " " + posY);
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;
        if (onScreen(monster.getMonsterSize()+1,screenX,screenY)) {
            panel.getMonsterAsset().getWitchEAssets().draw(screenX - panel.tileSize / 2, screenY - panel.tileSize / 2, spriteIndex, monster.getMonsterSize(), g2);
        }
    }

    public void update() {
        posX = monster.getPosX();
        posY = monster.getPosY();
        if(spriteIndex > 11) {
            monster.damage(1);
        }
        updateAnimation();
    }


    void updateAnimation() {
        if (--skillThread <= 0) {
            skillThread = 10;
            if (++spriteIndex >= 7)
            {
                spriteIndex = 0;
                casted = false;
            }
        }
    }
}
