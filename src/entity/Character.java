package entity;

import main.*;
import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Character extends Entity {
    final public int screenX;
    final public int screenY;
    protected int attackIndex, attackTick, attackInterval;

    public Character(Panel panel, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {

        super(panel, 1, skillThread);
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseEventHandler;
        this.attacking = false;
        direction = "down";

        // set vi tri cua map so voi man hinh
        posX = panel.tileSize * 11; // posX = 480
        posY = panel.tileSize * 10; // posY =480

        // set vi tri nhan vat so voi man hinh
        this.screenX = panel.screenWidth/2 - panel.tileSize; // screenX = 480
        this.screenY = panel.screenHeight/2 - panel.tileSize; // screenY = 276

        getPlayerImage();
    }

    public void getPlayerImage() {}

    public void update() {
        checkFlash();
        if (attacking) {
            updateAttackAnimation();
            moveAnimation();
            checkSkill();
            if(checkSkill&& skill.alive && attackIndex == 6) {
                skill.setSkill(posX, posY, direction, true, this);
            }

        } else if(flash) {
            moveFlashAnimation();
            moveAnimation();
        }
        else moveAnimation();
    }

    public void moveAnimation() {
        if (keyHandler.isMoving()) {
            if (keyHandler.upPressed){
                direction = "up";
            }
            if (keyHandler.downPressed){
                direction = "down";
            }
            if (keyHandler.leftPressed){
                direction = "left";
            }
            if (keyHandler.rightPressed){
                direction = "right";
            }

            // Check collision
            collisionDetected = false;
            panel.collisionHandler.checkMapCollision(this);
            panel.collisionHandler.checkMonsterCollision(this);
            if(!collisionDetected) {
                switch (direction) {
                    case "left":
                        posX -= speed;
                        break;
                    case "right":
                        posX += speed;
                        break;
                    case "up":
                        posY -= speed;
                        break;
                    case "down":
                        posY += speed;
                        break;
                }
            }

            updateSprite();
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 10) {
            if (++spriteNum > 5) spriteNum = 0;
            spriteCounter = 0;
        }
    }

    public void moveFlashAnimation() {
        if (keyHandler.flashPressed) {
            speed = 10;
            spriteCounter++;
            if (spriteCounter > 5) {
                if (spriteNum > 4) spriteNum = 1;
                else spriteNum ++;
                spriteCounter = 0;
                flash = false;
                speed = 2;
            }
        }
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
        }
        
        else
        {
            switch (direction) {
                case "up":
                    currentFrameImg = moveUp[spriteNum];
                    break;
                case "down":
                    currentFrameImg = moveDown[spriteNum];
                    break;
                case "left":
                    currentFrameImg = moveLeft[spriteNum];
                    break;
                case "right":
                    currentFrameImg = moveRight[spriteNum];
                    break;
            }

            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
        }
    }
    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex > 7 ) {
                attackIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkAttacking() {
        if(mouseHandler.isLeftClicked() || mouseHandler.isRightClicked()) {
            attacking = true;
        }
    }

    public void checkFlash() {
        if(keyHandler.flashPressed) {
            speed = 1000;
            flash = true;
        } else {
            speed = 2;         }
    }
    public void checkSkill(){
        if(keyHandler.isUsingSkill() && skill.alive) {
//            skill.setSkill(posX, posY, direction, true, this);
//
//            // ADD skill to The list
//            panel.skillList.add(skill);
            checkSkill = true;
        }

    }
}
