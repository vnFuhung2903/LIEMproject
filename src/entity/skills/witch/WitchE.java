package entity.skills.witch;

import entity.Entity;
import entity.Monster;
import entity.Skill;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class WitchE extends Skill {

    Monster monster;
    public WitchE(Panel panel, int speed, Entity entity, Monster monster) {
        super(panel, speed, 15, entity);
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

        panel.getMonsterAsset().getWitchEAssets().draw(screenX - panel.tileSize, screenY - panel.tileSize, spriteIndex, monster.getMonsterSize(), g2);
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
            skillThread = 15;
            if (++spriteIndex >= 7)
            {
                spriteIndex = 0;
                casted = false;
            }
        }
    }
}
