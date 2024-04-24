package map;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SandTrap {
    Panel panel;
    int posX, posY;
    BufferedImage[] Image;
    public SandTrap(Panel panel, int posX, int posY) {
        this.panel = panel;
        this.posX = posX;
        this.posY = posY;
        getImage();
    }

    void getImage() {
        try {
            Image = new BufferedImage[16];
            for(int i = 0; i < 9; ++i) {
                Image[i] = ImageIO.read(new File("assets/mapDesert/sandPit-0" + (i + 1) + ".png"));
            }

            for(int i = 9; i < 16; ++i) {
                Image[i] = ImageIO.read(new File("assets/mapDesert/sandPit-" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        checkHitBox();
    }

    public void draw(Graphics2D g2) {
        for(int i = 0; i < 4; ++i)
            for(int j = 0; j < 4; ++j) {

                int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX + i * panel.tileSize;
                int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY + j * panel.tileSize;

                if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                        posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                        posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                        posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY) {

                    g2.drawImage(Image[j * 4 + i], screenX, screenY, panel.tileSize, panel.tileSize, null);
                }
            }
    }

    void checkHitBox() {

        int playerLeftHitBox = panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x;
        int playerRightHitBox = playerLeftHitBox + panel.getPlayer().getHitBoxArea().width;
        int playerTopHitBox = panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y;
        int playerBottomHitBox = playerTopHitBox + panel.getPlayer().getHitBoxArea().height;
        if(playerLeftHitBox >= posX && playerRightHitBox <= posX + panel.tileSize * 4 && playerTopHitBox >= posY && playerBottomHitBox <= posY + panel.tileSize * 4) {
            panel.getPlayer().setSpeed(1);
            System.out.println("Sand trap");
        }
        else panel.getPlayer().setSpeed(5);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
