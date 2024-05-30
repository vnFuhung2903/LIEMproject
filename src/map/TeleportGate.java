package map;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TeleportGate {
    Panel panel;
    int posX, posY, imageIndex, tick;
    BufferedImage[] Image;
    Rectangle hitBoxArea;

    public TeleportGate(Panel panel, int posX, int posY) {
        this.panel = panel;
        this.posX = posX;
        this.posY = posY;
        this.hitBoxArea = new Rectangle(posX, posY, panel.tileSize * 4, panel.tileSize * 4);
        getItemImage();
    }

    public void getItemImage() {
        try {
            Image = new BufferedImage[10];
            for(int i = 0; i < 9; ++i) {
                Image[i] = ImageIO.read(new File("assets/blackHole/blackHole-0" + (i + 1) + ".png"));
            }
            Image[9] = ImageIO.read(new File("assets/blackHole/blackHole-10.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        checkHitBox();
        if(++tick >= 5) {
            tick = 0;
            if (++imageIndex >= 10) {
                imageIndex = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if(screenX >= Toolkit.getDefaultToolkit().getScreenSize().width || screenY >=Toolkit.getDefaultToolkit().getScreenSize().height) return;
        if(screenX + panel.tileSize * 4 <= 0 && screenY + panel.tileSize * 4 <= 0) return;
        g2.drawImage(Image[imageIndex], screenX, screenY, panel.tileSize * 4, panel.tileSize * 4, null);
    }

    public void checkHitBox() {
        if(posX < panel.getPlayer().getPosX() && posX + panel.tileSize * 4 > panel.getPlayer().getPosX() + panel.tileSize * 2 && posY < panel.getPlayer().getPosY() && posY + panel.tileSize * 4 > panel.getPlayer().getPosY() + panel.tileSize * 2) {
            panel.resetGame();
        }
    }
}