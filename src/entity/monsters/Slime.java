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
    public Slime(Panel panel, int speed, int skillThread, String color) {
        super(panel, speed, skillThread);
        this.name = "Slime";

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
        this.color = color;
        this.spriteInterval = 20;
        this.monsterSize = 1;
        this.collisionArea = new Rectangle(panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4);
        getMonsterImage();
    }

    public void getMonsterImage() {

        try {
            moveUp = new BufferedImage[7];
            moveDown = new BufferedImage[7];
            moveLeft = new BufferedImage[7];
            moveRight = new BufferedImage[7];

            for (int i = 0; i < 6;i++) {

                String fileMoveUp = "assets/slime/slime" + color + "MoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/slime/slime" + color + "MoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/slime/slime" + color + "MoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/slime/slime" + color + "MoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
