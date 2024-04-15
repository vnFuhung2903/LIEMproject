package entity.monsters;

import entity.Monster;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Slave extends Monster {
    public Slave(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);

        Random randomColor = new Random();
        int directionIndex = randomColor.nextInt(5);
        direction = "down";
        switch (directionIndex) {
            case 1:
                direction = "left";
                break;
            case 2:
                direction = "right";
                break;
            case 3:
                direction = "up";
                break;
            case 4:
                direction = "down";
                break;
        }

        this.spriteInterval = 20;
        this.maxSpriteNum = 1;
        this.monsterSize = 2;
        this.collisionArea = new Rectangle(panel.tileSize, panel.tileSize, 0, panel.tileSize / 2);
        getMonsterImage();
    }

    public void getMonsterImage() {

        try {
            moveUp = new BufferedImage[2];
            moveDown = new BufferedImage[2];
            moveLeft = new BufferedImage[2];
            moveRight = new BufferedImage[2];

            for (int i = 0; i < 2;i++) {

                String fileMoveUp = "assets/slave/slaveMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/slave/slaveMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/slave/slaveMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/slave/slaveMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            attackUp = new BufferedImage[6];
            attackDown = new BufferedImage[6];
            attackLeft = new BufferedImage[6];
            attackRight = new BufferedImage[6];

            for (int i = 0; i < 6;i++) {

                String fileAttackUp = "assets/slave/slaveAttackUp-0" + (i + 1) +".png";
                attackUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/slave/slaveAttackDown-0" + (i + 1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/slave/slaveAttackLeft-0" + (i + 1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/slave/slaveAttackRight-0" + (i + 1) +".png";
                attackRight[i] = ImageIO.read(new File(fileAttackRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

