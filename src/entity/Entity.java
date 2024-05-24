package entity;

import java.awt.*;

import main.*;
import main.Panel;

public abstract class Entity {

    protected Panel panel;
    protected int posX, posY, speed, moveCounter = 0, actionLockCounter = 0, skillThread, hp, maxHp,mana,maxMana;
    protected boolean attacking = false, stun = false;
    protected boolean flash = false;
    protected KeyHandler keyHandler;

    // For collision and hit box
    protected  boolean collisionDetected;
    protected Rectangle collisionArea;
    protected Rectangle hitBoxArea;
    protected String direction = "down";
    protected int spriteTick, spriteIndex, attackIndex, attackTick, attackInterval,spriteTickQSke,attackQIndex;

    public Entity(Panel panel, int speed, int skillThread) {
        this.panel = panel;
        this.speed = speed;
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

    public Rectangle getCollisionArea() { return collisionArea; }
    public Rectangle getHitBoxArea() { return hitBoxArea; }
    public String getDirection() { return direction; }
    public int getSpeed() { return speed; }
    public void detectCollision() { collisionDetected = true; }

    public void update() {}
    public void draw(Graphics2D g2) {}
    public void checkHitBox() {}
    public void damage(int damagePerHit) {}
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public boolean isStun() {
        return stun;
    }
    public boolean onScreen(int size, int screenX,int screenY) {
        if(screenX >= Toolkit.getDefaultToolkit().getScreenSize().width || screenY >=Toolkit.getDefaultToolkit().getScreenSize().height) return false;
        return screenX + size * panel.tileSize > 0 && screenY + size * panel.tileSize > 0;
    }
    public void setStun() {
        this.stun = true;
    }
    public void setNotStun() { this.stun = false; }
    public int getMana(){
        return mana;
    }
    public int getMaxMana(){
        return maxMana;
    }
}