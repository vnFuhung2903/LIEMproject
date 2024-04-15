package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import main.Panel;
public class Monster extends Entity {

    protected String name;
    protected int spriteInterval, maxSpriteNum = 5, monsterSize;
    public Monster(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
    }
    public void draw(Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        int screenX = posX - panel.player.posX + panel.player.screenX;
        int screenY = posY - panel.player.posY + panel.player.screenY;

        if (posX + panel.tileSize > panel.player.posX - panel.player.screenX &&
                posX - panel.tileSize < panel.player.posX + panel.player.screenX &&
                posY + panel.tileSize > panel.player.posY - panel.player.screenY &&
                posY - panel.tileSize < panel.player.posY + panel.player.screenY
        ) {
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
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * monsterSize, panel.tileSize * monsterSize, null);
        }

    }
    public void setAction() {
        if(++actionLockCounter == 200 ) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 25) {
                direction = "up";
            }
            else if (i <= 50) {
                direction = "down";
            }
            else if (i <= 75) {
                direction = "left";
            }
            else  {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    public void update() {
        setAction();
        if(++moveCounter == 10) {

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
        if (++spriteCounter > spriteInterval) {
            if (++spriteNum > maxSpriteNum) spriteNum = 0;
            spriteCounter = 0;
        }
    }
}
