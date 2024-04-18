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
    WitchQ witchQ;

    public Witch(Panel panel, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {
        super(panel, skillThread, keyHandler, mouseEventHandler);
        this.attackInterval = 10;
        this.collisionArea = new Rectangle(panel.tileSize , panel.tileSize , 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize, panel.tileSize);

        // Set up passive
        witchPassives = new WitchPassive[3];
        double angle = 0;
        for(int i = 0; i < 3; ++i) {
            witchPassives[i] = new WitchPassive(panel, 1, 10, this, angle);
            angle += 2 * Math.PI / 3;
        }

        // Set up skill Q
        witchQ = new WitchQ(panel, 1, 10, this);
        this.skillQ = witchQ;
    }

    public void update() {

        checkHitBox();
        for(int i = 0; i < 3; ++i) {
            witchPassives[i].update();
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

        for(int i = 0; i < 3; ++i) {
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

            for (int i =0; i < 6; ++i) {
                String fileMoveUp = "assets/witch/witchMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/witch/witchMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/witch/witchMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/witch/witchMoveRight-0" + (i + 1) +".png";
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

    public void checkAttacking() {

        if(keyHandler.isUsingSkillQ() && !witchQ.isCasted()) {
            attacking = true;
        }
    }

    public void checkHitBox() {
        for(int i = 0; i < 3; ++i) {
            witchPassives[i].checkHitBox();
        }
        if(usingSkillQ) {
            witchQ.checkHitBox();
        }
    }
}