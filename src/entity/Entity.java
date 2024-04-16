package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.KeyHandler;
import main.MouseEventHandler;
import main.Panel;

public abstract class Entity {

    protected Panel panel;
    protected int posX, posY;
    protected int speed;
    protected int moveCounter = 0;
    protected int actionLockCounter = 0;
    protected boolean attacking = false;
    protected int skillThread;
    protected boolean flash = false;
    protected boolean checkSkill = false;
    protected boolean alive;
    protected Skill skill;

    KeyHandler keyHandler;
    MouseEventHandler mouseHandler;

    // For Graphic
    protected BufferedImage[] fire;
    protected BufferedImage[] moveUp;
    protected BufferedImage[] moveDown;
    protected BufferedImage[] moveLeft;
    protected BufferedImage[] moveRight;
    protected BufferedImage[] attackUp;
    protected BufferedImage[] attackDown;
    protected BufferedImage[] attackLeft;
    protected BufferedImage[] attackRight;
    protected BufferedImage skillUp;
    protected BufferedImage skillDown;
    protected BufferedImage skillLeft;
    protected BufferedImage skillRight;

    // For collisions
    protected  boolean collisionDetected;
    protected Rectangle collisionArea;
    protected  Rectangle attackArea;

    protected String direction = "down";

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    public Entity(Panel panel, int speed, int skillThread) {
        this.panel = panel;
        this.speed = speed;
        this.alive = true;
        this.skillThread = skillThread;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int pos) { posX = pos; }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int pos) {
        posY = pos;
    }

    public boolean isAlive() { return alive; }
    public Rectangle getCollisionArea() { return collisionArea; }
    public String getDirection() { return direction; }
    public int getSpeed() { return speed; }
    public boolean isCollisionDetected() { return collisionDetected; }
    public void detectCollision() { collisionDetected = true; }

    public void update() {}
    public void draw(Graphics2D g2) {}
}