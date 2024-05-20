package entity.skills.witch;

import entity.Entity;
import entity.Skill;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WitchE extends Skill {

    BufferedImage[] Effect;
    int currentPosY, effectIndex;
    public WitchE(Panel panel, int speed, Entity entity) {
        super(panel, speed, 10, entity);
        getSkillImage();
    }

    void getSkillImage() {
        try {
            skillDown = new BufferedImage[8];
            Effect = new BufferedImage[7];

            for (int i = 0; i < 8; ++i) {
                skillDown[i] = ImageIO.read(new File("assets/witch/witchE/witchE-0" + (i + 1) + ".png"));
            }
            for (int i = 0; i < 7; ++i) {
                Effect[i] = ImageIO.read(new File("assets/witch/witchE/witchEEffect-0" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;
        System.out.print(screenX);
        System.out.println(screenY);
        g2.drawImage(Effect[effectIndex], screenX - panel.tileSize * (2 + effectIndex), screenY - panel.tileSize * (2 + effectIndex), panel.tileSize * 2 * (3 + effectIndex), panel.tileSize * 2 * (3 + effectIndex), null);
        g2.drawImage(skillDown[spriteIndex], screenX, currentPosY - panel.getPlayer().getPosY() + panel.getPlayer().screenY, panel.tileSize * 2, panel.tileSize * 2, null);
    }

    public void update() {
        if(currentPosY + 10 <= posY)
            currentPosY += 10;
        else currentPosY = posY;
        updateEffectAnimation();
        updateAnimation();
    }

    public void setSkill(int posX, int posY) {
        casted = true;
        spriteIndex = 0;
        effectIndex = 0;
        this.posX = posX;
        this.posY = posY;
        this.currentPosY = panel.getPlayer().getPosY() - panel.getPlayer().screenY;
        System.out.println("Creating a explosion at " + posX + " " + posY);
    }

    void updateEffectAnimation() {
        if (--skillThread <= 0) {
            if (++effectIndex >= 6)
                effectIndex = 0;
            skillThread = 12;
        }
    }

    void updateAnimation() {
        if (--skillThread <= 0) {
            skillThread = 10;
            if(currentPosY + 10 < posY && ++spriteIndex >= 2) {
                spriteIndex = 2;
                return;
            }
            if (++spriteIndex >= 8)
            {
                spriteIndex = 0;
                casted = false;
            }
        }
    }
}
