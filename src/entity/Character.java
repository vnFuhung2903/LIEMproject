package entity;

import main.*;
import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Character extends Entity {
    final public int screenX;
    final public int screenY;
    protected Skill skillQ, skillE;
    protected boolean usingSkillQ, usingSkillE, getHit;
    protected BufferedImage[] moveUp;
    protected BufferedImage[] moveDown;
    protected BufferedImage[] moveLeft;
    protected BufferedImage[] moveRight;
    protected BufferedImage[] attackUp;
    protected BufferedImage[] attackDown;
    protected BufferedImage[] attackLeft;
    protected BufferedImage[] attackRight;

    public Character(Panel panel, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {

        super(panel, 5, skillThread);
        this.keyHandler = keyHandler;
        this.attacking = false;
        direction = "down";

        // set vi tri cua map so voi man hinh
        posX = panel.mapWidth / 2; // posX = 480
        posY = panel.mapHeight / 2; // posY =480

        // set vi tri nhan vat so voi man hinh
        this.screenX = panel.screenWidth/2 - panel.tileSize; // screenX = 480
        this.screenY = panel.screenHeight/2 - panel.tileSize; // screenY = 276

        getPlayerImage();
    }

    public void getPlayerImage() {}

    public void moveAnimation() {

        if (stun) return;

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
//            panel.collisionHandler.checkMonsterCollision(this);
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

    public void updateSprite() {
        if (++spriteTick > 10) {
            if (++spriteIndex > 5) spriteIndex = 0;
            spriteTick = 0;
        }
    }

//    public void moveFlashAnimation() {
//        if (keyHandler.flashPressed) {
//            speed = 10;
//            if (++spriteTick > 5) {
//                if (spriteIndex > 4) spriteIndex = 1;
//                else spriteIndex ++;
//                spriteTick = 0;
//                flash = false;
//                speed = 2;
//            }
//        }
//    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex > 7 ) {
                attackIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkAttacking() {}

//    public void checkFlash() {
//        if(keyHandler.flashPressed) {
//            speed = 1000;
//            flash = true;
//        } else {
//            speed = 2;
//        }
//    }
}