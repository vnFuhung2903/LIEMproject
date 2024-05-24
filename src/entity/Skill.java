package entity;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Skill extends Entity {

    protected Entity user;
    protected boolean casted;
    protected BufferedImage[] skillUp;
    protected BufferedImage[] skillDown;
    protected BufferedImage[] skillLeft;
    protected BufferedImage[] skillRight;

    protected Skill(Panel panel, int speed, int skillThread, Entity user) {
        super(panel, speed, skillThread);
        this.user = user;
        casted = false;
    }

    public void setSkill(int posX, int posY) {}

    public boolean isCasted() { return casted;}
}