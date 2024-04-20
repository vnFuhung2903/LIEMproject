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
    int radius = 100, screenX, screenY;
    double angle;
    public WitchPassive(Panel panel, int speed, int skillThread, Entity user, double angle) {
        super(panel, speed, skillThread, user);
        this.angle = angle;
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize, panel.tileSize);
        getSkillImage();
    }

    public void update() {
        int speed = 100;
        angle += ((System.currentTimeMillis() % 10000) / (10000.0 * speed) * 2 * Math.PI);
        screenX = (int) (panel.getWidth() / 2 + radius * Math.cos(angle)) - panel.tileSize;
        screenY = (int) (panel.getHeight() / 2 + radius * Math.sin(angle)) - panel.tileSize;

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
                passiveImage[i] = ImageIO.read(new File("assets/witch/witchPassive/witchPassive-0" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(passiveImage[spriteIndex], screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
    }

    public void checkHitBox() {

        for(Monster monster : panel.getMonsters()) {

            Rectangle monsterHitBoxArea = monster.getHitBoxArea();
            int monsterLeftHitBox = monster.getPosX() + monsterHitBoxArea.x;
            int monsterRightHitBox = monsterLeftHitBox + monsterHitBoxArea.width;
            int monsterTopHitBox = monster.getPosY() + monsterHitBoxArea.y;
            int monsterBottomHitBox = monsterTopHitBox + monsterHitBoxArea.height;

            posX = panel.getPlayer().getPosX() + screenX - panel.getPlayer().screenX;
            posY = panel.getPlayer().getPosY() + screenY - panel.getPlayer().screenY;

            if(monsterLeftHitBox - panel.tileSize / 2 <= posX + hitBoxArea.x
                && monsterRightHitBox + panel.tileSize / 2 >= posX + hitBoxArea.x + hitBoxArea.width
                && monsterTopHitBox - panel.tileSize / 2 <= posY + hitBoxArea.y
                && monsterBottomHitBox + panel.tileSize / 2 >= posY  + hitBoxArea.y + hitBoxArea.height) {
                System.out.println("Passive hit");
                monster.damage(1);
            }
        }

    }
}
