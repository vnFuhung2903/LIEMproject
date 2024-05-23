package assets;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WitchQStun {
    Panel panel;
    BufferedImage[] Image;
    public WitchQStun(Panel panel) {
        this.panel = panel;
        getSkillImage();
    }

    void getSkillImage() {
        try {
            Image = new BufferedImage[4];

            for (int i = 0; i < 4; ++i) {
                Image[i] = ImageIO.read(new File("assets/witch/witchQEffect/witchQStunEffect-0" + (i + 1) + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(int screenX, int screenY, int index, int size, Graphics2D g2) {
        g2.drawImage(Image[index], screenX, screenY, panel.tileSize * (1 + size) + panel.tileSize / 2, panel.tileSize * (1 + size) + panel.tileSize / 2, null);
    }
}
