package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Slime {
    Panel panel;
    BufferedImage[][] moveUp;
    BufferedImage[][] moveDown;
    BufferedImage[][] moveLeft;
    BufferedImage[][] moveRight;

    public Slime(Panel panel) {
        this.panel = panel;
        getImage();
    }
    public void getImage() {
        try {
            moveUp = new BufferedImage[4][6];
            moveDown = new BufferedImage[4][6];
            moveLeft = new BufferedImage[4][6];
            moveRight = new BufferedImage[4][6];
            
            for(int id = 0; id < 4; ++id) {
                for (int i = 0; i < 6; ++i) {
                    String color = decodeColor(id);
                    String fileMoveUp = "assets/slimes/slime" + color + "MoveUp-0" + (i + 1) + ".png";
                    moveUp[id][i] = ImageIO.read(new File(fileMoveUp));
                    String fileMoveDown = "assets/slimes/slime" + color + "MoveDown-0" + (i + 1) + ".png";
                    moveDown[id][i] = ImageIO.read(new File(fileMoveDown));
                    String fileMoveLeft = "assets/slimes/slime" + color + "MoveLeft-0" + (i + 1) + ".png";
                    moveLeft[id][i] = ImageIO.read(new File(fileMoveLeft));
                    String fileMoveRight = "assets/slimes/slime" + color + "MoveRight-0" + (i + 1) + ".png";
                    moveRight[id][i] = ImageIO.read(new File(fileMoveRight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(entity.monsters.Slime slime, int screenX, int screenY, int index, Graphics2D g2) {
        BufferedImage currentFrameImg = null;
        int id = encodeColor(slime.getColor());
        System.out.println(slime.getColor());

        switch (slime.getDirection()) {
            case "up":
                currentFrameImg = moveUp[id][index];
                break;
            case "down":
                currentFrameImg = moveDown[id][index];
                break;
            case "left":
                currentFrameImg = moveLeft[id][index];
                break;
            case "right":
                currentFrameImg = moveRight[id][index];
                break;
        }
        g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
    
    int encodeColor(String color) {
        switch (color) {
            case "Red":
                return 0;
            case "Green":
                return 1;
            case "Blue":
                return 2;
            default:
                return 3;
        }
    }
    
    String decodeColor(int id) {
        switch (id) {
            case 0:
                return "Red";
            case 1:
                return "Green";
            case 2:
                return "Blue";
            default:
                return "Yellow";
        }
    }
}
