package assets;


import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SkeletonSlash {
    Panel panel;
    BufferedImage[] Image;
    public SkeletonSlash(Panel panel) {
        this.panel = panel;
        getSkillImage();
    }

    void getSkillImage() {
        try {
            Image = new BufferedImage[4];

            for (int i = 0; i < 4; ++i) {
                Image[i] = ImageIO.read(new File("assets/boss-skeleton/skeletonREffect/" + decodeDirection(i) + "Effect.png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(int screenX, int screenY, String direction, Graphics2D g2) {
        g2.drawImage(Image[encodeDirection(direction)], screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
    }

    int encodeDirection(String direction) {
        switch (direction) {
            case "left":
                return 0;
            case "right":
                return 1;
            case "up":
                return 2;
            default:
                return 3;
        }
    }

    String decodeDirection(int id) {
        switch (id) {
            case 0:
                return "left";
            case 1:
                return "right";
            case 2:
                return "up";
            default:
                return "down";
        }
    }
}
