package entity.monsters;
import entity.Entity;
import entity.Monster;
import main.CollisionHandler;
import main.Panel;
import org.w3c.dom.xpath.XPathNamespace;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Skeleton extends Monster {

    public Skeleton(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);

        Random randomColor = new Random();
        int directionIndex = randomColor.nextInt(5);
        switch (directionIndex) {
            case 1:
                direction = "left";
                break;
            case 2:
                direction = "right";
                break;
            case 3:
                direction = "up";
                break;
            case 4:
                direction = "down";
                break;
        }

        this.monsterSize = 4;
        this.attackInterval = 10;

        this.triggerArea = new Rectangle(-4 * panel.tileSize, -4 * panel.tileSize, 9 * panel.tileSize, 9 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize, panel.tileSize, panel.tileSize * 4, panel.tileSize * 4);

    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY
        ) {

            if (attacking) {
                panel.getMonsterAsset().getSkeletonAssets().draw(this,screenX,screenY,attackIndex,true,g2);
                return;
            }
            panel.getMonsterAsset().getSkeletonAssets().draw(this,screenX,screenY,spriteIndex,false,g2);
        }

    }

    public void setAction() {

        checkTriggerPlayer();
        if (++actionLockCounter == 50) {
            actionLockCounter = 0;

            if (triggering) {

                int distanceX = posX - panel.getPlayer().getPosX();
                int distanceY = posY - panel.getPlayer().getPosY();

                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    collisionDetected = false;
                    direction = distanceY < 0 ? "down" : "up";
                    panel.collisionHandler.checkMapCollision(this);
                    if (!collisionDetected)
                        return;
                } else {
                    collisionDetected = false;
                    direction = distanceX < 0 ? "right" : "left";
                    panel.collisionHandler.checkMapCollision(this);
                    if (!collisionDetected)
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
            if (++spriteIndex >= 3) spriteIndex = 0;
            spriteTick = 0;
        }
    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex >= 6) {
                attackIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkAttacking() {
        Rectangle checkAtack = new Rectangle(0,0,panel.tileSize,panel.tileSize);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(),panel.getPlayer().getPosY(),panel.tileSize, panel.tileSize);
        switch (direction) {
            case "up":
                checkAtack.x = posX + panel.tileSize/2;
                checkAtack.y = posY - panel.tileSize;
                checkAtack.width = panel.tileSize*2;
                checkAtack.height = panel.tileSize*1;
                if (checkAtack.intersects(playerArea)) {
                    attacking = true;
                }
                break;
            case "down":
                checkAtack.x = posX + panel.tileSize/2;
                checkAtack.y = posY + panel.tileSize * 3;
                checkAtack.width = panel.tileSize*2;
                checkAtack.height = panel.tileSize*1;
                if (checkAtack.intersects(playerArea)) {
                    attacking = true;
                }
                break;
            case "left":
                checkAtack.x = posX - panel.tileSize;
                checkAtack.y = posY + panel.tileSize/2;
                checkAtack.width = panel.tileSize * 1;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea)) {
                    attacking = true;
                }
                break;
            case "right":
                checkAtack.x = posX + panel.tileSize *3;
                checkAtack.y = posY + panel.tileSize/2;
                checkAtack.width = panel.tileSize*1;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea)) {
                    attacking = true;
                }
                break;
        }
    }
    public void checkHitBox() {

        if(!attacking) return;
        if(attackIndex < 4) return;


        Rectangle playerHitBoxArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y,
                panel.getPlayer().getHitBoxArea().width,
                panel.getPlayer().getHitBoxArea().height);

        Rectangle attackArea = new Rectangle(0,0,0,0);
        switch (direction){
            case "up":
                attackArea.x = posX + panel.tileSize/2;
                attackArea.y = posY - panel.tileSize;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*2;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Skeleton attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "down":
                attackArea.x = posX + panel.tileSize/2;
                attackArea.y = posY + panel.tileSize * 3;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*2;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Skeleton attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                attackArea.x = posX + panel.tileSize *3;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize*2;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Skeleton attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                attackArea.x = posX - panel.tileSize;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize * 2;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("Skeleton attack");
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }

}


