package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Ghost {
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
    BufferedImage[] invisible;


    public Ghost(Panel panel) {
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

            qUp = new BufferedImage[1];
            qDown = new BufferedImage[1];
            qLeft = new BufferedImage[1];
            qRight = new BufferedImage[1];

            invisible = new BufferedImage[1];

            for (int i = 0; i < 6;i++) {
                String fileMoveUp = "assets/boss-ghost/move/ghostMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/boss-ghost/move/ghostMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/boss-ghost/move/ghostMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/boss-ghost/move/ghostMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            for (int i = 0; i < 6;i++) {
                String fileAttackUp = "assets/boss-ghost/ghostE/ghostEUp-0" + (i + 1) +".png";
                eUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/boss-ghost/ghostE/ghostEDown-0" + (i + 1) + ".png";
                eDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/boss-ghost/ghostE/ghostELeft-0" + (i + 1) +".png";
                eLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/boss-ghost/ghostE/ghostERight-0" + (i + 1) +".png";
                eRight[i] = ImageIO.read(new File(fileAttackRight));
            }
            for(int i=0;i<1;i++){
                String fileAttackUp = "assets/boss-ghost/ghost-01.png";
                invisible[i] = ImageIO.read(new File(fileAttackUp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw1(entity.monsters.Ghost ghost, int screenX, int screenY, int index, boolean attacking, Graphics2D g2) {
        BufferedImage currentFrameImg = null;

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