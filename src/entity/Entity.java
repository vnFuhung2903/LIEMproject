package entity;

import java.awt.image.BufferedImage;

import main.Panel;

public abstract class Entity {

    Panel panel;
    public int posX, posY;
    protected int speed;
    protected int moveCounter = 0;
    protected int actionLockCounter = 0;
    protected boolean attacking = false;
    protected int skillThread;
    protected boolean flash = false;
    protected boolean alive;


    protected BufferedImage[] moveUp;
    protected BufferedImage[] moveDown;
    protected BufferedImage[] moveLeft;
    protected BufferedImage[] moveRight;
    protected BufferedImage[] punchUp;
    protected BufferedImage[] punchDown;
    protected BufferedImage[] punchLeft;
    protected BufferedImage[] punchRight;
    protected BufferedImage skillUp;
    protected BufferedImage skillDown;
    protected BufferedImage skillLeft;
    protected BufferedImage skillRight;

    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Entity(Panel panel, int speed, int skillThread) {
        this.panel = panel;
        this.speed = speed;
        this.alive = true;
        this.skillThread = skillThread;
    }
}