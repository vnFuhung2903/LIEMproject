package entity.monsters;

import entity.Monster;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Slime extends Monster {

    String color;
    public Slime(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
        this.name = "Slime";

        Random randomColor = new Random();
        int colorIndex = randomColor.nextInt(5);
        switch (colorIndex % 4) {
            case 0:
                color = "Blue";
                break;
            case 1:
                color = "Green";
                break;
            case 2:
                color = "Red";
                break;
            case 3:
                color = "Yellow";
                break;
        }
        setRandomDirection();

        this.monsterSize = 1;
        this.triggerArea = null;
        this.hp = 100;
        this.collisionArea = new Rectangle(panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4);
        this.hitBoxArea = new Rectangle(0, 0, panel.tileSize, panel.tileSize);
        getMonsterImage();
    }

    public void getMonsterImage() {

        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];

            for (int i = 0; i < 6;i++) {

                String fileMoveUp = "assets/slimes/slime" + color + "MoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/slimes/slime" + color + "MoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/slimes/slime" + color + "MoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/slimes/slime" + color + "MoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(color);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY
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
            setRandomDirection();
        }
    }

    public void updateSprite() {
        if (++spriteTick > 10) {
            if (++spriteIndex >= 6) spriteIndex = 0;
            spriteTick = 0;
        }
    }

    public void checkHitBox() {

        if(posX >= panel.getPlayer().getPosX() - panel.tileSize && posX <= panel.getPlayer().getPosX() + panel.tileSize * 3 && posY >= panel.getPlayer().getPosY() - panel.tileSize && posY <= panel.getPlayer().getPosY() + panel.tileSize * 3) {
            switch (color) {
                case "red":
                    System.out.println("Take burn");
                    panel.setEffect(panel.getPlayer(), "burn", 10, 2);
                    break;
                case "green":
                    System.out.println("Take heal");
                    panel.setEffect(panel.getPlayer(), "healing", 10, 2);
                    break;
                case "blue":
                    System.out.println("Take ice");
                    panel.setEffect(panel.getPlayer(), "ice", 10, 2);
                    break;
            }
        }
    }
}