package entity;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Effect {
    Panel panel;
    String name;
    Entity entity;
    int posX, posY, effectInterval, effectTick = 0, effectCounter = 0, effectIndex = 0, maxImageNum, entitySize = 2;
    boolean active;
    BufferedImage[] Image;

    public Effect(Panel panel, Entity entity, String name, int interval, int entitySize) {
        this.panel = panel;
        this.entity = entity;
        this.name = name;
        this.posX = entity.getPosX();
        this.posY = entity.getPosY();
        switch (name) {
            case "burn":
                maxImageNum = 7;
                break;
            case "healing":
                maxImageNum = 5;
                break;
        }
        this.active = true;
        this.entitySize = entitySize;
        this.effectInterval = interval;
        getItemImage();
    }

    public void getItemImage() {

        try {
            Image = new BufferedImage[maxImageNum];
            for(int i = 0; i < maxImageNum; ++i) {
                Image[i] = ImageIO.read(new File("assets/effects/" + name + "Effect-0" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(effectIndex == maxImageNum) {
            switch (name) {
                case "burn":
                    burn(entity);
                    break;
                case "healing":
                    heal(entity);
                    break;
            }
        }

        posX = entity.getPosX();
        posY = entity.getPosY();

        if(++effectCounter >= 20) {
            effectCounter = 0;
            if (++effectTick >= effectInterval) {
                effectTick = 0;
                if (++effectIndex > maxImageNum) {
                    effectIndex = 0;
                    active = false;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY
        ) {
            g2.drawImage(Image[effectIndex], screenX, screenY, panel.tileSize * entitySize, panel.tileSize * entitySize, null);
        }
    }

    public void burn(Entity entity) {
        entity.hp -= 1;
    }
    public void heal(Entity entity) {
        entity.hp += 1;
    }
    public void  extend(int interval) {
        effectInterval += interval;
    }

    public boolean isActive() {
        return active;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }
}
