package entity.skills.witch;

import entity.*;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WitchQ extends Skill {

    public WitchQ(Panel panel, int speed, int skillThread, Entity user) {
        super(panel, speed, skillThread, user);
        getSkillImage();
    }

    void getSkillImage() {
        try {
            skillUp = new BufferedImage[3];
            skillDown = new BufferedImage[3];
            skillLeft = new BufferedImage[3];
            skillRight = new BufferedImage[3];

            for (int i = 0; i < 3; ++i) {
                String fileAttackUp = "assets/witch/witchQEffect/witchQEffectUp-0" + (i + 1) + ".png";
                skillUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/witch/witchQEffect/witchQEffectDown-0" + (i + 1) + ".png";
                skillDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/witch/witchQEffect/witchQEffectLeft-0" + (i + 1) + ".png";
                skillLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/witch/witchQEffect/witchQEffectRight-0" + (i + 1) + ".png";
                skillRight[i] = ImageIO.read(new File(fileAttackRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (--skillThread <= 0) {
            if (++spriteIndex > 2)
            {
                spriteIndex = 0;
                casted = false;
            }
            skillThread = 10;
        }
    }

    // Not set pos for Q because laze will change direction with its user
    public void setSkill() {
        casted = true;
        spriteIndex = 0;
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = panel.getPlayer().screenX;
        int screenY = panel.getPlayer().screenY;

        switch (panel.getPlayer().getDirection()) {
            case "up":
                g2.drawImage(skillUp[spriteIndex], screenX, screenY - panel.tileSize * 19 / 4, panel.tileSize * 2, panel.tileSize * 6, null);
                break;
            case "down":
                g2.drawImage(skillDown[spriteIndex], screenX, screenY, panel.tileSize * 2, panel.tileSize * 6, null);
                break;
            case "left":
                g2.drawImage(skillLeft[spriteIndex], screenX - panel.tileSize * 4, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                break;
            case "right":
                g2.drawImage(skillRight[spriteIndex], screenX, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                break;
        }
    }

    public void checkHitBox() {
        ArrayList<Monster> monsters = panel.getMonsters();

        for(Monster monster : monsters) {

            Rectangle monsterHitBoxArea = monster.getHitBoxArea();
            int monsterLeftHitBox = monster.getPosX() + monsterHitBoxArea.x;
            int monsterRightHitBox = monsterLeftHitBox + monsterHitBoxArea.width;
            int monsterTopHitBox = monster.getPosY() + monsterHitBoxArea.y;
            int monsterBottomHitBox = monsterTopHitBox + monsterHitBoxArea.height;

            switch (panel.getPlayer().getDirection()) {
                case "up":
                    posX = panel.getPlayer().getPosX();
                    posY = panel.getPlayer().getPosY() - panel.tileSize * 5;
                    if(monsterLeftHitBox - panel.tileSize / 2 <= posX + panel.tileSize / 2
                        && monsterRightHitBox + panel.tileSize / 2 >= posX + panel.tileSize * 3 / 2
                        && monsterTopHitBox >= posY
                        && monsterBottomHitBox <= posY + panel.tileSize * 6) {
                        System.out.println("Q hit");
                        monster.damage(1);
                    }
                    break;
                case "down":
                    posX = panel.getPlayer().getPosX();
                    posY = panel.getPlayer().getPosY();
                    if(monsterLeftHitBox - panel.tileSize / 2 <= posX + panel.tileSize / 2
                        && monsterRightHitBox + panel.tileSize / 2 >= posX + panel.tileSize * 3 / 2
                        && monsterTopHitBox >= posY
                        && monsterBottomHitBox <= posY + panel.tileSize * 6) {
                        System.out.println("Q hit");
                        monster.damage(1);
                    }
                    break;
                case "left":
                    posX = panel.getPlayer().getPosX() - panel.tileSize * 4;
                    posY = panel.getPlayer().getPosY();
                    if(monsterLeftHitBox >= posX
                        && monsterRightHitBox <= posX + panel.tileSize * 6
                        && monsterTopHitBox - panel.tileSize / 2 <= posY + panel.tileSize / 2
                        && monsterBottomHitBox + panel.tileSize / 2 >= posY + panel.tileSize * 3 / 2) {
                        System.out.println("Q hit");
                        monster.damage(1);
                    }
                    break;
                case "right":
                    posX = panel.getPlayer().getPosX();
                    posY = panel.getPlayer().getPosY();
                    if(monsterLeftHitBox >= posX
                        && monsterRightHitBox >= posX + panel.tileSize * 6
                        && monsterTopHitBox - panel.tileSize / 2 <= posY + panel.tileSize / 2
                        && monsterBottomHitBox + panel.tileSize / 2 >= posY + panel.tileSize * 3 / 2) {
                        System.out.println("Q hit");
                        monster.damage(1);
                    }
                    break;
            }
        }
    }
}