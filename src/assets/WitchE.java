package assets;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class WitchE {

    Panel panel;
    BufferedImage[] Image;
    public WitchE(Panel panel) {
        this.panel = panel;
        getSkillImage();
    }

    void getSkillImage() {
        try {
            Image = new BufferedImage[7];

            for (int i = 0; i < 7; ++i) {
                Image[i] = ImageIO.read(new File("assets/witch/witchE/witchEEffect-0" + (i + 1) + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(int screenX, int screenY, int index, int size, Graphics2D g2) {
        g2.drawImage(Image[index], screenX, screenY, panel.tileSize * (2 + size) + panel.tileSize / 2, panel.tileSize * (2 + size) + panel.tileSize / 2, null);
    }
}
