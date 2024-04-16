package entity;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Skill extends Entity {

    Entity user;
    protected Skill(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
    }

    public void setSkill(int posX, int posY, String direction, boolean alive, Entity user) {
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;

    }

    public void update() {

    }

    public void draw(Graphics2D g2) {

    }
}