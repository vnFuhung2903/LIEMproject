package entity.skills;

import entity.*;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WitchPassive extends Skill {

    BufferedImage[] passiveImage;
    int radius = 100;
    double angle;
    public WitchPassive(Panel panel, int speed, int skillThread, Entity user) {
        super(panel, speed, skillThread, user);
        getSkillImage();
    }

    public void update() {
        int speed = 100;
        angle += ((System.currentTimeMillis() % 10000) / (10000.0 * speed) * 2 * Math.PI);
        posX = (int) (panel.getWidth() / 2 + radius * Math.cos(angle));
        posY = (int) (panel.getHeight() / 2 + radius * Math.sin(angle));

        updateSprite();
    }

    void updateSprite() {
        if (++spriteTick > 3) {
            if (++spriteIndex > 8) spriteIndex = 0;
            spriteTick = 0;
        }
    }

    public void getSkillImage() {
        passiveImage = new BufferedImage[9];

        try {
            for (int i = 0; i < 9; ++i) {
                passiveImage[i] = ImageIO.read(new File("assets/witch/witchPassive/fire-0" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(passiveImage[spriteIndex], posX - panel.tileSize, posY - panel.tileSize, panel.tileSize * 2, panel.tileSize * 2, null);
    }

    public void setAngle(double angle) { this.angle = angle; }
}
