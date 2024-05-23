package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Panel;
public class Monster extends Entity {

    protected String name;
    protected int monsterSize, attackIndex, attackTick = 0,attackTickQ=0, attackInterval;
    protected Rectangle triggerArea;
    protected int counterTrigger = 0;
    protected boolean triggering = false;
    protected boolean readySkillE= true,readySkillQ = true,readySkillR = true;
    public Monster(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
        this.hp = 200;
    }

    public void setAction() {}

    public void setRandomDirection() {

        Random random = new Random();
        int i = random.nextInt(100) + 1;

        if (i <= 25) {
            direction = "up";
        } else if (i <= 50) {
            direction = "down";
        } else if (i <= 75) {
            direction = "left";
        } else {
            direction = "right";
        }
    }

    public void update() {

        checkAttacking();
        checkHitBox();
        if(attacking) {
            updateAttackAnimation();
        }
        else{
            move();
        }
    }

    public void move() {

        setAction();
        if(++moveCounter == 3) {

            //Check collision
            collisionDetected = false;
            panel.collisionHandler.checkMapCollision(this);
            if(!collisionDetected) {
                switch (direction) {
                    case "up":
                        posY -= speed;
                        break;
                    case "down":
                        posY += speed;
                        break;
                    case "left":
                        posX -= speed;
                        break;
                    case "right":
                        posX += speed;
                        break;
                }
            }
            moveCounter = 0;
        }

        updateSprite();
    }

    public void updateSprite() {}

    public void updateAttackAnimation() {}

    public void checkTriggerPlayer(){
        if(++counterTrigger > 300){
            counterTrigger = 0;
        }
        if(counterTrigger > 0) return;
        if(triggerArea == null) return;
        Rectangle monsterTrigger = new Rectangle(posX + triggerArea.x,posY + triggerArea.y,triggerArea.width, triggerArea.height);
        Rectangle playerTrigger = new Rectangle(panel.getPlayer().getPosX(),panel.getPlayer().getPosY(),panel.tileSize,panel.tileSize);
        if(monsterTrigger.intersects(playerTrigger)) {
            triggering = true;
        }
    }

    public void damage(int damagePerHit) {
        this.hp -= damagePerHit;
        if(this.hp <= 0) {
            panel.createItem(1, posX + panel.tileSize * (monsterSize - 1), posY + panel.tileSize * (monsterSize - 1));
        }
    }

    public void checkAttacking() {}

    public int getMonsterSize() {
        return monsterSize;
    }
}