package entity.monsters;

import entity.Monster;
import main.Panel;

import java.awt.*;
import java.util.Random;

public class Slave extends Monster {
    public Slave(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);

        setRandomDirection();
        this.monsterSize = 2;
        this.attackInterval = 10;

        this.triggerArea = new Rectangle(-3 * panel.tileSize, -3 * panel.tileSize, 8 * panel.tileSize, 8 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize, panel.tileSize, 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize, panel.tileSize);
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (onScreen(2,screenX,screenY)) {

            if (attacking) {
                panel.getAsset().getSlaveAssets().draw(this, screenX, screenY, attackIndex, true, g2);
                return;
            }
            panel.getAsset().getSlaveAssets().draw(this, screenX, screenY, spriteIndex, false, g2);
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
            if (++spriteIndex > 1) spriteIndex = 0;
            spriteTick = 0;
        }
    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex > 5 ) {
                attackIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkAttacking() {

        if(posX >= panel.getPlayer().getPosX() && posX <= panel.getPlayer().getPosX() + panel.tileSize && posY >= panel.getPlayer().getPosY() - panel.tileSize / 2 && posY <= panel.getPlayer().getPosY() + panel.tileSize / 2)
            attacking = true;

        if(posX <= panel.getPlayer().getPosX() && posX >= panel.getPlayer().getPosX() - panel.tileSize && posY >= panel.getPlayer().getPosY() - panel.tileSize / 2 && posY <= panel.getPlayer().getPosY() + panel.tileSize / 2)
            attacking = true;

        if(posX >= panel.getPlayer().getPosX() - panel.tileSize / 2 && posX <= panel.getPlayer().getPosX() + panel.tileSize / 2 && posY <= panel.getPlayer().getPosY() && posY >= panel.getPlayer().getPosY() - panel.tileSize)
            attacking = true;

        if(posX >= panel.getPlayer().getPosX() - panel.tileSize / 2 && posX <= panel.getPlayer().getPosX() + panel.tileSize / 2 && posY >= panel.getPlayer().getPosY() && posY <= panel.getPlayer().getPosY() + panel.tileSize)
            attacking = true;
    }

    public void checkHitBox() {

        if(!attacking)      return;
        if(attackIndex < 4) return;

        Rectangle playerHitBoxArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y,
                panel.getPlayer().getHitBoxArea().width,
                panel.getPlayer().getHitBoxArea().height);

        Rectangle attackArea = new Rectangle(0,0,panel.tileSize,panel.tileSize);
        switch (direction){
            case "up":
                attackArea.x = posX;
                attackArea.y = posY - panel.tileSize;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("Slave attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "down":
                attackArea.x = posX;
                attackArea.y = posY + panel.tileSize;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("Slave attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                attackArea.x = posX + panel.tileSize;
                attackArea.y = posY;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("Slave attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                attackArea.x = posX - panel.tileSize;
                attackArea.y = posY;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("Slave attack");
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }
}