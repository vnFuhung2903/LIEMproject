package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Spider {
    Panel panel;
    BufferedImage[] moveUp;
    BufferedImage[] moveDown;
    BufferedImage[] moveLeft;
    BufferedImage[] moveRight;

    public Spider(Panel panel) {
        this.panel = panel;
        getImage();
    }
    public void getImage() {
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

    public void draw(entity.monsters.Spider spider, int screenX, int screenY, int index, Graphics2D g2) {
        BufferedImage currentFrameImg = null;

        switch (spider.getDirection()) {
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
        g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
}
