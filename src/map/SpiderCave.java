package map;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpiderCave {
    Panel panel;
    int posX, posY;
    Tile tile;
    Rectangle territoryArea;

    public SpiderCave(Panel panel, int posX, int posY) {
        this.panel = panel;
        this.posX = posX;
        this.posY = posY;
        this.tile = new Tile();
        this.territoryArea = new Rectangle(-panel.tileSize * 4, panel.tileSize * 4, panel.tileSize * 9, panel.tileSize * 9);
        getImage();
    }

    public void getImage() {
        try {
            tile.image = ImageIO.read(new File("assets/mapDesert/SpiderCave-01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if(screenX >= Toolkit.getDefaultToolkit().getScreenSize().width || screenY >=Toolkit.getDefaultToolkit().getScreenSize().height) return;
        if(screenX + panel.tileSize <= 0 && screenY + panel.tileSize <= 0) return;
        g2.drawImage(tile.image, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle getTerritoryArea() {
        return territoryArea;
    }
}
