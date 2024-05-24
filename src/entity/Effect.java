package entity;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Effect {
    private Panel panel;
    private String name;
    private Entity entity;
    private int posX, posY, effectInterval, effectTick = 0, effectIndex = 0, maxImageNum, entitySize = 1, effectTime, effectTimeAnimation;
    private boolean active;
    private BufferedImage[] images;

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
        loadEffectImages();
    }

    private void setEffectProperties(String name) {
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
            case "dispel":
                maxImageNum = 6;
                effectTimeAnimation = 5;
                break;

        }
    }

    private void loadEffectImages() {
        try {
            images = new BufferedImage[maxImageNum];
            for (int i = 0; i < maxImageNum; i++) {
                images[i] = ImageIO.read(new File("assets/effects/" + name + "Effect-0" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (!entity.dispel) {
            applyBadEffect(entity);
        }

        switch (name){
            case "dispel":
                dispel(entity);
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
                if (name == "ice"|| name == "dispel") {
                    effectIndex = maxImageNum - 1;
                } else {
                    effectIndex = 0;
                }

                if (--effectTime == 0) {
                    active = false;
                    if ( name == "dispel" ) entity.dispel = false;
                    if (name == "ice") entity.setNotStun();
                    effectTime = 5;
                }
            }
        }
    }

    private void applyBadEffect(Entity entity) {
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
                break;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        g2.drawImage(images[effectIndex], screenX, screenY, panel.tileSize * entitySize, panel.tileSize * entitySize, null);
    }

    private void burn(Entity entity) {
        if (entity.getHp() > 0) {
            entity.damage(2);
        }
    }

    private void poison(Entity entity) {
        if (entity.getHp() > 0) {
            entity.damage(entity.getHp() / 1000);
        }
    }

    private void heal(Entity entity) {
        if (entity.getHp() < entity.getMaxHp()) {
            entity.hp ++ ;
        }
    }

    private void healingMana(Entity entity) {
        if (entity.getMana() < entity.getMaxMana()) {
            entity.mana++;
        }
    }

    private void stun(Entity entity) {
        entity.setStun();
    }

    private void dispel(Entity entity) {
        entity.dispel = true;
    }

    public void extend(int time) {
        effectInterval += time;
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