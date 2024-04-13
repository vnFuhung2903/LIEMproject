package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import main.Panel;
public class Monster extends Entity {
    public Monster(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
        this.direction = "down";
        getMonsterImage();
    }

    public void getMonsterImage() {

        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];

            //get Move
            int move = 6;
            for (int i =0; i< move;i++) {

                String fileMoveUp = "assets/slime/slimeMoveUp-0" + (i+1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/slime/slimeMoveDown-0" + (i+1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/slime/slimeMoveLeft-0" + (i+1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/slime/slimeMoveRight-0" + (i+1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void draw(Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        int screenX = posX - panel.player.posX + panel.player.screenX;
        int screenY = posY - panel.player.posY + panel.player.screenY;

        if ( posX + panel.tileSize > panel.player.posX - panel.player.screenX &&
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
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize, panel.tileSize, null);

        }

    }
    public void setAction() {
        actionLockCounter++;
        if(actionLockCounter == 200 ) {
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
        moveCounter ++;
        if(moveCounter == 10) {
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
            moveCounter = 0;
        }
        spriteCounter++;
        if (spriteCounter > 20) {
            if (spriteNum > 4) spriteNum = 0;
            else spriteNum ++;
            spriteCounter = 0;
        }
    }
}
