package entity.characters;

import entity.Character;
import main.*;
import main.Panel;
import entity.skills.WitchPassive;
import entity.skills.WitchQ;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Witch extends Character {

    WitchPassive[] witchPassives;
    BufferedImage[] getHitUp;
    BufferedImage[] getHitDown;
    BufferedImage[] getHitLeft;
    BufferedImage[] getHitRight;
    BufferedImage[] passiveBackground;

    WitchQ witchQ;

    int passiveInterval = 15, getHitInterval = 10, getHitIndex = 0, passiveIndex = 0;

    public Witch(Panel panel, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {
        super(panel, skillThread, keyHandler, mouseEventHandler);
        this.attackInterval = 10;
        this.collisionArea = new Rectangle(panel.tileSize , panel.tileSize , 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize, panel.tileSize);

        // Set up passive
        witchPassives = new WitchPassive[6];
        double angle = 0;
        for(int i = 0; i < 6; ++i) {
            witchPassives[i] = new WitchPassive(panel, 1, 10, this, angle);
            angle += 2 * Math.PI / 6;
        }

        // Set up skill Q
        witchQ = new WitchQ(panel, 1, 10, this);
        this.skillQ = witchQ;
    }

    public void update() {

        checkHitBox();
        for(int i = 0; i < 6; ++i) {
            updatePassiveAnimation();
            witchPassives[i].update();
        }

        if (stun) return;

        if(getHit) {
            updateGetHitAnimation();
            moveAnimation();
            return;
        }

        if(usingSkillQ) witchQ.update();

        if (attacking) {
            updateAttackAnimation();
            if(attackIndex >= 5 && !witchQ.isCasted()) {
                witchQ.setSkill();
                usingSkillQ = true;
            }
        }
        else moveAnimation();
    }

    public void draw(Graphics2D g2) {

        BufferedImage currentFrameImg = null;

        checkAttacking();

        g2.drawImage(passiveBackground[passiveIndex], screenX - panel.tileSize * 2 + panel.tileSize / 4, screenY - panel.tileSize * 2 + panel.tileSize / 4, panel.tileSize * 6 - panel.tileSize / 2, panel.tileSize * 6 - panel.tileSize / 2, null);

        if(getHit) {
            switch (direction) {
                case "up":
                    currentFrameImg = getHitUp[getHitIndex];
                    break;
                case "down":
                    currentFrameImg = getHitDown[getHitIndex];
                    break;
                case "left":
                    currentFrameImg = getHitLeft[getHitIndex];
                    break;
                case "right":
                    currentFrameImg = getHitRight[getHitIndex];
                    break;
            }

            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
        }

        else if (attacking) {
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

        for(int i = 0; i < 6; ++i) {
            witchPassives[i].draw(g2);
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
                String fileAttackUp = "assets/witch/witchAttack/witchAttackUp-0" + (i + 1) +".png";
                attackUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/witch/witchAttack/witchAttackDown-0" + (i + 1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/witch/witchAttack/witchAttackLeft-0" + (i + 1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/witch/witchAttack/witchAttackRight-0" + (i + 1) +".png";
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
        if(keyHandler.isUsingSkillQ() && !witchQ.isCasted()) {
            attacking = true;
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
}