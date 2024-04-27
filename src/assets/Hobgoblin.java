package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Hobgoblin {
    Panel panel;
    BufferedImage[] moveUp;
    BufferedImage[] moveDown;
    BufferedImage[] moveLeft;
    BufferedImage[] moveRight;
    BufferedImage[] attackUp;
    BufferedImage[] attackDown;
    BufferedImage[] attackLeft;
    BufferedImage[] attackRight;
    
    public Hobgoblin(Panel panel) {
        this.panel = panel;
        getImage();
    }
    public void getImage() {
        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];

            attackUp = new BufferedImage[9];
            attackDown = new BufferedImage[9];
            attackLeft = new BufferedImage[9];
            attackRight = new BufferedImage[9];

            for (int i = 0; i < 6;i++) {
                String fileMoveUp = "assets/hobgoblin/godlinMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/hobgoblin/godlinMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/hobgoblin/godlinMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/hobgoblin/godlinMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            for (int i = 0; i < 9;i++) {
                String fileAttackUp = "assets/hobgoblin/godlinAttackUp-0" + (i + 1) +".png";
                attackUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/hobgoblin/godlinAttackDown-0" + (i + 1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/hobgoblin/godlinAttackLeft-0" + (i + 1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/hobgoblin/godlinAttackRight-0" + (i + 1) +".png";
                attackRight[i] = ImageIO.read(new File(fileAttackRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(entity.monsters.Hobgoblin hobgoblin, int screenX, int screenY, int index, boolean attacking, Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        if(attacking) {
            switch (hobgoblin.getDirection()) {
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
            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 3, panel.tileSize * 3, null);
            return;
        }

        switch (hobgoblin.getDirection()) {
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
        g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 3, panel.tileSize * 3, null);
    }
}
