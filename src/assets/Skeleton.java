package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Skeleton {
    Panel panel;
    BufferedImage[] moveUp;
    BufferedImage[] moveDown;
    BufferedImage[] moveLeft;
    BufferedImage[] moveRight;
    BufferedImage[] eUp;
    BufferedImage[] eDown;
    BufferedImage[] eLeft;
    BufferedImage[] eRight;
    BufferedImage[] qUp;
    BufferedImage[] qDown;
    BufferedImage[] qLeft;
    BufferedImage[] qRight;


    public Skeleton(Panel panel) {
        this.panel = panel;
        getImage();
    }
    public void getImage() {
        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];

            eUp = new BufferedImage[9];
            eDown = new BufferedImage[9];
            eLeft = new BufferedImage[9];
            eRight = new BufferedImage[9];

            qUp = new BufferedImage[3];
            qDown = new BufferedImage[3];
            qLeft = new BufferedImage[3];
            qRight = new BufferedImage[3];

            for (int i = 0; i < 6;i++) {
                String fileMoveUp = "assets/boss-skeleton/move/skeletonMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/boss-skeleton/move/skeletonMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/boss-skeleton/move/skeletonMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/boss-skeleton/move/skeletonMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            for (int i = 0; i < 6;i++) {
                String fileAttackUp = "assets/boss-skeleton/skeletonE/skeletonEUp-0" + (i + 1) +".png";
                eUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/boss-skeleton/skeletonE/skeletonEDown-0" + (i + 1) + ".png";
                eDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/boss-skeleton/skeletonE/skeletonELeft-0" + (i + 1) +".png";
                eLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/boss-skeleton/skeletonE/skeletonERight-0" + (i + 1) +".png";
                eRight[i] = ImageIO.read(new File(fileAttackRight));
            }
            for (int i = 0; i < 6;i++) {
                String fileAttackUp = "assets/boss-skeleton/skeletonQ/skeletonQUp-0" + (i + 1) +".png";
                qUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/boss-skeleton/skeletonQ/skeletonQDown-0" + (i + 1) + ".png";
                qDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/boss-skeleton/skeletonQ/skeletonQLeft-0" + (i + 1) +".png";
                qLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/boss-skeleton/skeletonQ/skeletonQRight-0" + (i + 1) +".png";
                qRight[i] = ImageIO.read(new File(fileAttackRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(entity.monsters.Ghost ghost, int screenX, int screenY, int index, boolean attacking, Graphics2D g2) {
        BufferedImage currentFrameImg = null;
//        if(checkQ){
//            switch (ghost.getDirection()) {
//                case "up":
//                    currentFrameImg =qUp[index];
//                    break;
//                case "down":
//                    currentFrameImg = qDown[index];
//                    break;
//                case "left":
//                    currentFrameImg =qLeft[index];
//                    break;
//                case "right":
//                    currentFrameImg = qRight[index];
//                    break;
//            }
//            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
//            return;
//        }
        if(attacking) {
            switch (ghost.getDirection()) {
                case "up":
                    currentFrameImg =eUp[index];
                    break;
                case "down":
                    currentFrameImg = eDown[index];
                    break;
                case "left":
                    currentFrameImg = eLeft[index];
                    break;
                case "right":
                    currentFrameImg = eRight[index];
                    break;
            }
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
            return;
        }

        switch (ghost.getDirection()) {
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
        g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
    }
}
