package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Slave {
    Panel panel;
    BufferedImage[] moveUp;
    BufferedImage[] moveDown;
    BufferedImage[] moveLeft;
    BufferedImage[] moveRight;
    BufferedImage[] attackUp;
    BufferedImage[] attackDown;
    BufferedImage[] attackLeft;
    BufferedImage[] attackRight;

    public Slave(Panel panel) {
        this.panel = panel;
        getImage();
    }
    public void getImage() {
        try {
            moveUp = new BufferedImage[2];
            moveDown = new BufferedImage[2];
            moveLeft = new BufferedImage[2];
            moveRight = new BufferedImage[2];

            attackUp = new BufferedImage[6];
            attackDown = new BufferedImage[6];
            attackLeft = new BufferedImage[6];
            attackRight = new BufferedImage[6];

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

    public void draw(entity.monsters.Slave slave, int screenX, int screenY, int index, boolean attacking, Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        if(attacking) {
            switch (slave.getDirection()) {
                case "up":
                    currentFrameImg = attackUp[index];
                    break;
                case "down":
                    currentFrameImg = attackDown[index];
                    break;
                case "left":
                    currentFrameImg = attackLeft[index];
                    break;
                case "right":
                    currentFrameImg = attackRight[index];
                    break;
            }
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
            return;
        }

        switch (slave.getDirection()) {
            case "up":
                currentFrameImg = moveUp[index];
                break;
            case "down":
                currentFrameImg = moveDown[index];
                break;
            case "left":
                currentFrameImg = moveLeft[index];
                break;
            case "right":
                currentFrameImg = moveRight[index];
                break;
        }
        g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
    }
}
