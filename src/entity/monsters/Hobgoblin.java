package entity.monsters;

import entity.Monster;
import main.Panel;

import java.awt.*;
import java.util.Random;

public class Hobgoblin extends Monster {
    public Hobgoblin(Panel panel, int speed, int skillThread) {
    super(panel, speed, skillThread);

        setRandomDirection();
        this.monsterSize = 2;
        this.attackInterval = 10;

        this.triggerArea = new Rectangle(-4 * panel.tileSize, -4 * panel.tileSize, 9 * panel.tileSize, 9 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize, panel.tileSize, 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize * 3 / 2, panel.tileSize * 3 / 2);
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (onScreen(3,screenX,screenY)) {
            if (attacking) {
                panel.getAsset().getHobgoblinAsset().draw(this, screenX, screenY, attackIndex, true, g2);
                return;
            }
            panel.getAsset().getHobgoblinAsset().draw(this, screenX, screenY, spriteIndex, false, g2);
        }
    }

    public void setAction() {

        checkTriggerPlayer();
        if(++actionLockCounter == 50) {
            actionLockCounter = 0;

            if (triggering) {

                int distanceX = posX - panel.getPlayer().getPosX();
                int distanceY = posY - panel.getPlayer().getPosY();

                if(Math.abs(distanceX) < Math.abs(distanceY)) {
                    collisionDetected = false;
                    direction = distanceY < 0 ? "down" : "up";
                    panel.collisionHandler.checkMapCollision(this);
                    if(!collisionDetected)
                        return;
                }

                else {
                    collisionDetected = false;
                    direction = distanceX < 0 ? "right" : "left";
                    panel.collisionHandler.checkMapCollision(this);
                    if(!collisionDetected)
                        return;
                }

                collisionDetected = false;
                triggering = false;
            }

            setRandomDirection();
        }
    }

    public void updateSprite() {
        if (++spriteTick > 10) {
            if (++spriteIndex >= 6) spriteIndex = 0;
            spriteTick = 0;
        }
    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex >= 9) {
                attackIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkAttacking() {
        Rectangle checkAttacking = new Rectangle(0, 0, panel.tileSize*2, panel.tileSize*2);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(), panel.getPlayer().getPosY(), panel.tileSize * 2, panel.tileSize * 2);
        switch (direction) {
            case "up":
                checkAttacking.x = posX + panel.tileSize / 2;
                checkAttacking.y = posY - panel.tileSize*3/2;
                if (checkAttacking.intersects(playerArea)) {
                    attacking =true;
                }
                break;
            case "down":
                checkAttacking.x = posX + panel.tileSize / 2;
                checkAttacking.y = posY + panel.tileSize * 5/2;
                if (checkAttacking.intersects(playerArea)) {
                    attacking =true;
                }
                break;
            case "left":
                checkAttacking.x = posX - panel.tileSize*3/2;
                checkAttacking.y = posY + panel.tileSize / 2;
                if (checkAttacking.intersects(playerArea) ) {
                    attacking = true;
                }
                break;
            case "right":
                checkAttacking.x = posX + panel.tileSize * 5/2;
                checkAttacking.y = posY + panel.tileSize / 2;
                if (checkAttacking.intersects(playerArea)) {
                    attacking = true;
                }
                break;
        }
    }

    public void checkHitBox() {

        if(!attacking)      return;
        if(attackIndex < 4) return;


        Rectangle playerHitBoxArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y,
                panel.getPlayer().getHitBoxArea().width,
                panel.getPlayer().getHitBoxArea().height);

        Rectangle attackArea = new Rectangle(0,0,panel.tileSize*2,panel.tileSize*2);
        switch (direction){
            case "up":
                attackArea.x = posX + panel.tileSize / 2;
                attackArea.y = posY - panel.tileSize * 3 / 2;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Hobgoblin attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "down":
                attackArea.x = posX + panel.tileSize / 2;
                attackArea.y = posY + panel.tileSize * 5 / 2;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Hobgoblin attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                attackArea.x = posX + panel.tileSize * 5 / 2;
                attackArea.y = posY + panel.tileSize / 2;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Hobgoblin attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                attackArea.x = posX - panel.tileSize * 3 / 2;
                attackArea.y = posY + panel.tileSize / 2;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Hobgoblin attack");
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }
}