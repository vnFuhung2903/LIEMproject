package entity.characters;

import entity.Character;
import entity.Monster;
import entity.skills.witch.WitchE;
import main.*;
import main.Panel;
import entity.skills.witch.WitchPassive;
import entity.skills.witch.WitchQ;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Witch extends Character {

    WitchPassive[] witchPassives;
    BufferedImage[] getHitUp;
    BufferedImage[] getHitDown;
    BufferedImage[] getHitLeft;
    BufferedImage[] getHitRight;
    BufferedImage[] passiveBackground;

    WitchQ witchQ;

    int passiveInterval = 15, getHitInterval = 10, getHitIndex = 0, passiveIndex = 0;
    boolean preparingQ;

    public Witch(Panel panel, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {
        super(panel, skillThread, keyHandler, mouseEventHandler);
        this.attackInterval = 10;
        this.hp = 2000;
        this.speed = 1;
        this.maxHp = 2000;
        this.mana = 1000;
        this.maxMana = 1000;
        this.collisionArea = new Rectangle(panel.tileSize , panel.tileSize , 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize, panel.tileSize);

        // Set up passive
        witchPassives = new WitchPassive[6];
        double angle = 0;
        for(int i = 0; i < 6; ++i) {
            witchPassives[i] = new WitchPassive(panel, 1, 10, this, angle);
            angle += 2 * Math.PI / 6;
        }

        // Set up skill
        witchQ = new WitchQ(panel, 1, 10, this);
        this.skillQ = witchQ;
    }

    public void update() {
        cooldownQ();
        cooldownE();
        checkHitBox();

        for(int i = 0; i < 6; ++i) {
            updatePassiveAnimation();
            witchPassives[i].update();
        }

        if(usingSkillQ) {
            witchQ.update();
            mana -= 2;
        }

        if (attacking) {

            updateAttackAnimation();
            if(attackIndex >= 5 && preparingQ) {
                witchQ.setSkill();
                usingSkillQ = true;
                preparingQ = false;
            }

            if(usingSkillE) {
                ArrayList<Monster> monsters = panel.getMonsters();
                for(Monster monster : monsters) {
                    Rectangle checkE = new Rectangle(panel.getPlayer().getPosX()-4*panel.tileSize,
                            panel.getPlayer().getPosY()-panel.tileSize*4,
                            panel.tileSize*10,panel.tileSize*10);
                    Rectangle monsterArea = new Rectangle(monster.getPosX(),monster.getPosY(),
                            monster.getMonsterSize()*panel.tileSize,monster.getMonsterSize()*panel.tileSize);
                    if(checkE.intersects(monsterArea)) {
                        WitchE witchE = new WitchE(panel, 10, this, monster);
                        panel.setSkill(witchE);
                    }
                }
                mana -=60;
                usingSkillE = false;
            }
        }
        else moveAnimation();
    }

    public void draw(Graphics2D g2) {

        BufferedImage currentFrameImg = null;

        checkAttacking();
        g2.drawImage(passiveBackground[passiveIndex], screenX - panel.tileSize * 2 + panel.tileSize / 4, screenY - panel.tileSize * 2 + panel.tileSize / 4, panel.tileSize * 6 - panel.tileSize / 2, panel.tileSize * 6 - panel.tileSize / 2, null);

        for (int i = 0; i < 6; ++i) {
            witchPassives[i].draw(g2);
        }
            if (attacking) {
            switch (direction) {
                case "up":
                    currentFrameImg = attackUp[attackIndex];
                    break;
                case "down":
                    currentFrameImg = attackDown[attackIndex];
                    break;
                case "left":
                    currentFrameImg = attackLeft[attackIndex];
                    break;
                case "right":
                    currentFrameImg = attackRight[attackIndex];
                    break;
            }

            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);

            if(usingSkillQ) witchQ.draw(g2);
        }

        else
        {
            switch (direction) {
                case "up":
                    currentFrameImg = moveUp[spriteIndex];
                    break;
                case "down":
                    currentFrameImg = moveDown[spriteIndex];
                    break;
                case "left":
                    currentFrameImg = moveLeft[spriteIndex];
                    break;
                case "right":
                    currentFrameImg = moveRight[spriteIndex];
                    break;
            }

            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
        }
    }

    public void getPlayerImage() {
        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];

            attackUp = new BufferedImage[8];
            attackDown = new BufferedImage[8];
            attackLeft = new BufferedImage[8];
            attackRight = new BufferedImage[8];

            getHitUp = new BufferedImage[2];
            getHitDown = new BufferedImage[2];
            getHitLeft = new BufferedImage[2];
            getHitRight = new BufferedImage[2];

            passiveBackground = new BufferedImage[6];

            for (int i =0; i < 6; ++i) {
                String fileMoveUp = "assets/witch/witchMove/witchMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/witch/witchMove/witchMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/witch/witchMove/witchMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/witch/witchMove/witchMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            for(int i = 0; i < 8; ++i) {
                String fileAttackUp = "assets/witch/witchQ/witchQUp-0" + (i + 1) +".png";
                attackUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/witch/witchQ/witchQDown-0" + (i + 1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/witch/witchQ/witchQLeft-0" + (i + 1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/witch/witchQ/witchQRight-0" + (i + 1) +".png";
                attackRight[i] = ImageIO.read(new File(fileAttackRight));
            }

            for(int i = 0; i < 2; ++i) {
                String fileGetHitUp = "assets/witch/witchGetHit/witchGetHitUp-0" + (i + 1) +".png";
                getHitUp[i] = ImageIO.read(new File(fileGetHitUp));
                String fileGetHitDown = "assets/witch/witchGetHit/witchGetHitDown-0" + (i + 1) + ".png";
                getHitDown[i] = ImageIO.read(new File(fileGetHitDown));
                String fileGetHitLeft = "assets/witch/witchGetHit/witchGetHitLeft-0" + (i + 1) +".png";
                getHitLeft[i] = ImageIO.read(new File(fileGetHitLeft));
                String fileGetHitRight = "assets/witch/witchGetHit/witchGetHitRight-0" + (i + 1) +".png";
                getHitRight[i] = ImageIO.read(new File(fileGetHitRight));
            }

            for(int i = 0; i < 6; ++i) {
                passiveBackground[i] = ImageIO.read(new File("assets/witch/witchPassive/passive-0" + (i + 1) + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex > 7 ) {
                attackIndex = 0;
                attacking = false;
                usingSkillQ = false;
            }
        }
    }

    public void updateGetHitAnimation() {
        if (--getHitInterval <= 0) {
            getHitInterval = 10;
            if (++getHitIndex > 1 ) {
                getHitIndex = 0;
                getHit = false;
            }
        }
    }

    public void updatePassiveAnimation() {
        if (--passiveInterval <= 0) {
            passiveInterval = 100;
            if (++passiveIndex >= 6) {
                passiveIndex = 0;
            }
        }
    }

    public void checkAttacking() {
        if(keyHandler.isUsingSkillQ() && !witchQ.isCasted() && readyQ) {
            attacking = true;
            preparingQ = true;
            readyQ = false;
        }

        if(keyHandler.isUsingSkillE() && readyE) {
            attacking = true;
            usingSkillE = true;
            readyE = false;
        }
    }

    public void checkHitBox() {
        for(int i = 0; i < 6; ++i) {
            witchPassives[i].checkHitBox();
        }
        if(usingSkillQ) {
            witchQ.checkHitBox();
        }
    }

    public void damage(int damagePerHit) {
        hp -= damagePerHit;
        getHit = true;
        spriteIndex = 0;
    }
    public void cooldownQ(){
        if(!readyQ){
            if(++counterQ > 200){
                counterQ = 0;
                readyQ = true;
            }
        }
    }
    public void cooldownE(){
        if(!readyE){
            if(++counterE > 200){
                counterE = 0;
                readyE = true;
            }
        }
    }

}