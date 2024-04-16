package entity.monsters;

import entity.Monster;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Spider extends Monster {
    public Spider(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);

        Random randomColor = new Random();
        int directionIndex = randomColor.nextInt(5);
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

        this.spriteInterval = 10;
        this.monsterSize = 1;
        this.maxSpriteNum = 5;
        this.triggerArea = new Rectangle(-5 * panel.tileSize, -5 * panel.tileSize, 11 * panel.tileSize, 11 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4);
        getMonsterImage();
    }

    public void getMonsterImage() {

        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];

            for (int i = 0; i < 6;i++) {

                String fileMoveUp = "assets/spider/spiderMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/spider/spiderMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/spider/spiderMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/spider/spiderMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
