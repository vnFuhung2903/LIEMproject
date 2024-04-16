package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Panel;
public class Monster extends Entity {

    protected String name;
    protected int monsterSize, attackIndex, attackTick = 0, attackInterval;
    protected Rectangle triggerArea;
    protected boolean triggering = false;
    public Monster(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        int screenX = posX - panel.getPlayer().posX + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().posY + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().posX - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().posX + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().posY - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().posY + panel.getPlayer().screenY
        ) {
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
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * monsterSize, panel.tileSize * monsterSize, null);
        }

    }
    public void setAction() {
        if(++actionLockCounter == 50) {
            actionLockCounter = 0;

            // Random path
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
        }
    }
    public void update() {

        checkAttacking();
        if(attacking) {
            System.out.println("attack");
            updateAttackAnimation();
        }
        move();
    }

    public void updateSprite() {}

    public void move() {
        setAction();
        if(++moveCounter == 3) {

            //Check collision
            collisionDetected = false;
            panel.collisionHandler.checkMapCollision(this);
            if(!collisionDetected) {
                switch (direction) {
                    case "up":
                        posY -= speed;
                        break;
                    case "down":
                        posY += speed;
                        break;
                    case "left":
                        posX -= speed;
                        break;
                    case "right":
                        posX += speed;
                        break;
                }
            }
            moveCounter = 0;
        }
        updateSprite();
    }

    public void updateAttackAnimation() {}

    public void checkTriggerPlayer() {

        triggering = false;
        if(triggerArea == null) return;

        int monsterLeftTrigger = posX + triggerArea.x;
        int monsterRightTrigger = monsterLeftTrigger + triggerArea.width;
        int monsterTopTrigger = posY + triggerArea.y;
        int monsterBottomTrigger = monsterTopTrigger + triggerArea.height;

        int playerLeftTrigger = panel.getPlayer().getPosX();
        int playerRightTrigger = playerLeftTrigger + panel.tileSize * 2;
        int playerTopTrigger = panel.getPlayer().getPosY();
        int playerBottomTrigger = playerTopTrigger + panel.tileSize * 2;

        if(playerLeftTrigger >= monsterLeftTrigger && playerRightTrigger <= monsterRightTrigger && playerTopTrigger >= monsterTopTrigger && playerBottomTrigger <= monsterBottomTrigger) {
            triggering = true;
        }
    }

    public void checkAttacking() {}
}