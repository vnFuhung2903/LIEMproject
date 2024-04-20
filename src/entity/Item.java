package entity;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item {
    Panel panel;
    int id;
    int posX, posY;
    boolean collectable;
    BufferedImage Image;
    Rectangle hitBoxArea;

    public Item(Panel panel, int id, int posX, int posY) {
        this.panel = panel;
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.hitBoxArea = new Rectangle(posX, posY, panel.tileSize, panel.tileSize);
        this.collectable = true;
        getItemImage();
    }

    public void getItemImage() {

        try {
            Image = ImageIO.read(new File("assets/items/item-0" + id + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        checkHitBox();
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY
        ) {
            g2.drawImage(Image, screenX, screenY, panel.tileSize, panel.tileSize, null);
        }
    }

    public void checkHitBox() {
        if(posX > panel.getPlayer().getPosX() - panel.tileSize && posX < panel.getPlayer().getPosX() + panel.tileSize * 3 && posY > panel.getPlayer().getPosY() - panel.tileSize && posY <= panel.getPlayer().getPosY() + panel.tileSize * 3) {
            System.out.println("Take item");
            this.collectable = false;
            panel.collectItem();
        }
    }

    public boolean isCollectable() { return collectable; }
}
