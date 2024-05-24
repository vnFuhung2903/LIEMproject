package entity;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Effect {
    Panel panel;
    String name;
    Entity entity;
    int posX, posY, effectInterval, effectTick = 0, effectIndex = 0, maxImageNum, entitySize = 1, effectTime,effectTimeAnimation;
    boolean active;
    BufferedImage[] Image;

    public Effect(Panel panel, Entity entity, String name, int time, int entitySize) {
        this.panel = panel;
        this.entity = entity;
        this.name = name;
        this.posX = entity.getPosX();
        this.posY = entity.getPosY();
        this.entitySize = entitySize;
        this.effectTime = time;
        setEffectProperties(name);
        this.active = true;
        loadImage();
    }

    void setEffectProperties(String name) {
        switch (name) {
            case "burn":
                maxImageNum = 7;
                effectTimeAnimation = 5;
                break;
            case "poison":
                maxImageNum = 7;
                effectTimeAnimation = 10;
                break;
            case "healing":
                maxImageNum = 5;
                effectTimeAnimation = 12;
                break;
            case "ice":
                maxImageNum = 3;
                effectTimeAnimation = 5;
                break;
            case "healingMana":
                maxImageNum = 5;
                effectTimeAnimation = 5;
                break;
            case "ghostPassive":
                maxImageNum = 9;
                effectTimeAnimation = 2;
                break;
            case "immune":
                maxImageNum = 6;
                effectTimeAnimation = 5;
                break;

        }
    }

    void loadImage() {

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

        if (!entity.immune) {
            applyBadEffect(entity);
        }

        switch (name){
            case "immune":
                immune(entity);
                break;
            case "healing":
                heal(entity);
                break;
        }

        posX = entity.getPosX();
        posY = entity.getPosY();

        if (++effectTick >= effectTimeAnimation) {
            effectTick = 0;
            if (++effectIndex >= maxImageNum) {
                if (Objects.equals(name, "ice") || Objects.equals(name, "immune")) {
                    effectIndex = maxImageNum - 1;
                } else {
                    effectIndex = 0;
                }

                if (--effectTime == 0) {
                    active = false;
                    if (Objects.equals(name, "immune")) entity.immune = false;
                    if (Objects.equals(name, "ice")) entity.setNotStun();
                    if (Objects.equals(name, "ghostPassive")) entity.setSpeed(entity.getSpeed() * 5);
                    effectTime = 5;
                }
            }
        }
    }

    void applyBadEffect(Entity entity) {
        switch (name) {
            case "burn":
                burn(entity);
                break;
            case "ice":
                stun(entity);
                break;
            case "poison":
                poison(entity);
                break;
            case "healingMana":
                healingMana(entity);
                break;
            case "ghostPassive":
                slow(entity);
                break;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        g2.drawImage(Image[effectIndex], screenX, screenY, panel.tileSize * entitySize, panel.tileSize * entitySize, null);
    }

    void burn(Entity entity) {
        if (entity.getHp() > 0) {
            entity.damage(2);
        }
    }

    void poison(Entity entity) {
        if (entity.getHp() > 0) {
            entity.damage(entity.getHp() / 1000);
        }
    }

    void heal(Entity entity) {
        if (entity.getHp() < entity.getMaxHp()) {
            entity.hp ++ ;
        }
    }

    void healingMana(Entity entity) {
        if (entity.getMana() < entity.getMaxMana()) {
            entity.mana++;
        }
    }

    void stun(Entity entity) {
        entity.setStun();
    }

    void immune(Entity entity) {
        entity.immune = true;
    }
    
    void slow(Entity entity) {
        entity.setSpeed(entity.getSpeed() / 5);
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
