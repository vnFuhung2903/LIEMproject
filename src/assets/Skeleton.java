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
    BufferedImage[] rUp;
    BufferedImage[] rDown;
    BufferedImage[] rLeft;
    BufferedImage[] rRight;
    BufferedImage [] skillQ;

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

            eUp = new BufferedImage[6];
            eDown = new BufferedImage[6];
            eLeft = new BufferedImage[6];
            eRight = new BufferedImage[6];

            qUp = new BufferedImage[8];
            qDown = new BufferedImage[8];
            qLeft = new BufferedImage[8];
            qRight = new BufferedImage[8];

            rUp = new BufferedImage[6];
            rDown = new BufferedImage[6];
            rLeft = new BufferedImage[6];
            rRight = new BufferedImage[6];

            skillQ= new BufferedImage[4];

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
                String fileAttackEUp = "assets/boss-skeleton/skeletonE/skeletonEUp-0" + (i + 1) +".png";
                eUp[i] = ImageIO.read(new File(fileAttackEUp));
                String fileAttackEDown = "assets/boss-skeleton/skeletonE/skeletonEDown-0" + (i + 1) + ".png";
                eDown[i] = ImageIO.read(new File(fileAttackEDown));
                String fileAttackELeft = "assets/boss-skeleton/skeletonE/skeletonELeft-0" + (i + 1) +".png";
                eLeft[i] = ImageIO.read(new File(fileAttackELeft));
                String fileAttackERight = "assets/boss-skeleton/skeletonE/skeletonERight-0" + (i + 1) +".png";
                eRight[i] = ImageIO.read(new File(fileAttackERight));
            }
            for (int i = 0; i < 8;i++) {
                String fileAttackQUp = "assets/boss-skeleton/skeletonQ/skeletonQUp-0" + (i + 1) +".png";
                qUp[i] = ImageIO.read(new File(fileAttackQUp));
                String fileAttackQDown = "assets/boss-skeleton/skeletonQ/skeletonQDown-0" + (i + 1) + ".png";
                qDown[i] = ImageIO.read(new File(fileAttackQDown));
                String fileAttackQLeft = "assets/boss-skeleton/skeletonQ/skeletonQLeft-0" + (i + 1) +".png";
                qLeft[i] = ImageIO.read(new File(fileAttackQLeft));
                String fileAttackQRight = "assets/boss-skeleton/skeletonQ/skeletonQRight-0" + (i + 1) +".png";
                qRight[i] = ImageIO.read(new File(fileAttackQRight));
            }
            for (int i = 0; i < 6;i++) {
                String fileAttackUp = "assets/boss-skeleton/skeletonR/skeletonRUp-0" + (i + 1) +".png";
                rUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/boss-skeleton/skeletonR/skeletonRDown-0" + (i + 1) + ".png";
                rDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/boss-skeleton/skeletonR/skeletonRLeft-0" + (i + 1) +".png";
                rLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/boss-skeleton/skeletonR/skeletonRRight-0" + (i + 1) +".png";
                rRight[i] = ImageIO.read(new File(fileAttackRight));
            }
            for (int i=0; i<4; i++){
                String fileQEffect = "assets/boss-skeleton/skeletonQEffect/skeletonQEffect-0" + (i+1) + ".png";
                skillQ[i] = ImageIO.read(new File(fileQEffect));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawQEffect(entity.monsters.Skeleton skeleton, int screenX, int screenY, int index, Graphics2D g2){
        switch (skeleton.getDirection()){
            case "up":
                g2.drawImage(skillQ[index], screenX, screenY-panel.tileSize*4, panel.tileSize * 4, panel.tileSize * 4, null);
                break;
            case "down" :
                g2.drawImage(skillQ[index], screenX, screenY+panel.tileSize*4, panel.tileSize * 4, panel.tileSize * 4, null);
                break;
            case "left" :
                g2.drawImage(skillQ[index], screenX - panel.tileSize*4, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
                break;
            case "right":
                g2.drawImage(skillQ[index], screenX + panel.tileSize*4, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
                break;
        }
    }
    public void drawE(entity.monsters.Skeleton skeleton, int screenX, int screenY, int index, boolean checkE, Graphics2D g2){
        BufferedImage currentFrameImg = null;
        if(checkE){
            switch (skeleton.getDirection()) {
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

        }
    }
    public void drawQ(entity.monsters.Skeleton skeleton, int screenX, int screenY, int index, boolean checkQ, Graphics2D g2){
        BufferedImage currentFrameImg = null;
        if(checkQ){
            switch (skeleton.getDirection()) {
                case "up":
                    currentFrameImg =qUp[index];
                    break;
                case "down":
                    currentFrameImg = qDown[index];
                    break;
                case "left":
                    currentFrameImg = qLeft[index];
                    break;
                case "right":
                    currentFrameImg = qRight[index];
                    break;
            }
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);

        }
    }

    public void draw(entity.monsters.Skeleton skeleton, int screenX, int screenY, int index, boolean attacking, Graphics2D g2) {
        BufferedImage currentFrameImg = null;
        if(attacking) {
            switch (skeleton.getDirection()) {
                case "up":
                    currentFrameImg =rUp[index];
                    break;
                case "down":
                    currentFrameImg = rDown[index];
                    break;
                case "left":
                    currentFrameImg = rLeft[index];
                    break;
                case "right":
                    currentFrameImg = rRight[index];
                    break;
            }
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
            return;
        }

        switch (skeleton.getDirection()) {
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
