package map;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Nightmode {
    Panel panel;
    Tile tile;

    public Nightmode(Panel panel) {
        this.panel = panel;
        tile = new Tile();
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile.image = ImageIO.read(new File("assets/map/night-01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(tile.image, 0, 0, panel.mapWidth, panel.mapHeight, null);
    }
}
