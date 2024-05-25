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
                g2.drawImage(skillUp[spriteIndex], screenX, screenY - panel.tileSize * 5, panel.tileSize * 2, panel.tileSize * 6, null);
                break;
            case "down":
                g2.drawImage(skillDown[spriteIndex], screenX, screenY, panel.tileSize * 2, panel.tileSize * 6, null);
                break;
            case "left":
                g2.drawImage(skillLeft[spriteIndex], screenX - panel.tileSize * 18/4, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                break;
            case "right":
                g2.drawImage(skillRight[spriteIndex], screenX, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                break;
        }
    }

    public void checkHitBox() {
        ArrayList<Monster> monsters = panel.getMonsters();

        for(Monster monster : monsters) {

            Rectangle monsterHitBoxArea = new Rectangle(monster.getPosX()+monster.getHitBoxArea().x,
                    monster.getPosY() + monster.getHitBoxArea().y,
                    monster.getHitBoxArea().width,monster.getHitBoxArea().height);
            Rectangle areaQ = new Rectangle(0,0,0,0);


            switch (panel.getPlayer().getDirection()) {
                case "up":
                    areaQ.x = panel.getPlayer().getPosX() + panel.tileSize / 2;
                    areaQ.y = panel.getPlayer().getPosY() - panel.tileSize * 5;
                    areaQ.width = panel.tileSize;
                    areaQ.height = panel.tileSize * 6;
                    if(areaQ.intersects(monsterHitBoxArea)){
                        panel.setWitchSkillEffects(monster, 10);
                        System.out.println("dame");
                        monster.damage(1);
                    }
                    break;
                case "down":
                    areaQ.x = panel.getPlayer().getPosX() + panel.tileSize * 2;
                    areaQ.y = panel.getPlayer().getPosY() + panel.tileSize;
                    areaQ.width = panel.tileSize;
                    areaQ.height = panel.tileSize * 6;
                    if(areaQ.intersects(monsterHitBoxArea)) {
                        panel.setWitchSkillEffects(monster, 10);
                        System.out.println("dame");
                        monster.damage(1);
                    }
                    break;
                case "left":
                    areaQ.x = panel.getPlayer().getPosX() - panel.tileSize * 5;
                    areaQ.y = panel.getPlayer().getPosY() + panel.tileSize / 2;
                    areaQ.width = panel.tileSize * 6;
                    areaQ.height = panel.tileSize;
                    if(areaQ.intersects(monsterHitBoxArea)) {
                        panel.setWitchSkillEffects(monster, 10);
                        System.out.println("dame");
                        monster.damage(1);
                    }
                    break;
                case "right":
                    areaQ.x = panel.getPlayer().getPosX() + panel.tileSize;
                    areaQ.y = panel.getPlayer().getPosY() + panel.tileSize / 2;
                    areaQ.width = panel.tileSize * 6;
                    areaQ.height = panel.tileSize;
                    if(areaQ.intersects(monsterHitBoxArea)) {
                        panel.setWitchSkillEffects(monster, 10);
                        System.out.println("dame");
                        monster.damage(1);
                    }
                    break;
            }
        }
    }
}