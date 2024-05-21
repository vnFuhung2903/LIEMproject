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
            if(skillE() && readySkillE){
                if(++counterE <150){
                    panel.getMonsterAsset().getSkeletonAssets().drawE(this,screenX,screenY,spriteIndex,true,g2);
                    hitBoxArea.width =0;
                    hitBoxArea.height = 0;
//                    System.out.println(hp);
                }
                if(counterE>=150){
                    hp +=30;
                    counterE = 0;
                    readySkillE = false;
                    hitBoxArea.width = panel.tileSize*4;
                    hitBoxArea.height = panel.tileSize *4;
                }
                return;
            }
            cooldownE();
            if (attacking) {
                panel.getMonsterAsset().getSkeletonAssets().draw(this,screenX,screenY,attackIndex,true,g2);
                return;
            }

            if(attackQ){
                    panel.getMonsterAsset().getSkeletonAssets().drawQ(this,screenX,screenY,spriteTickQSke,true,g2);
                    panel.getMonsterAsset().getSkeletonAssets().drawQEffect(this,screenX,screenY,attackQIndex,g2);
                return;
            }

            panel.getMonsterAsset().getSkeletonAssets().draw(this,screenX,screenY,spriteIndex,false,g2);
//            System.out.println(hp);
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
    @Override
    public void update(){
        checkAttacking();
        checkAttackingQ();
        if(attackQ){
            updateAttackAnimationQ();
            updateAttackAnimationQEffect();
            checkHitBoxQ();
        }
        else if(attacking){
            updateAttackAnimation();
            checkHitBox();
        }
        else {
            move();
        }
    }

    public void updateSprite() {
        if (++spriteTick > 10) {
            if (++spriteIndex >= 6) spriteIndex = 0;
            spriteTick = 0;
        }
    }
    public void updateAttackAnimationQEffect(){
        if(--skillThread < 0){
            if(++attackQIndex > 3){
                attackQIndex =0;
                attackQ = false;
            }
            skillThread = 10;
        }
    }
    public void updateAttackAnimationQ(){
        if(++attackTickQ >= attackInterval){
            attackTickQ = 0;
            if(++spriteTickQSke >=6){
                spriteTickQSke =0;
            }
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
    public boolean skillE(){
        if(getHp() <100){
            return true;
        }
        return false;
    }
    public void cooldownE(){
        if(readySkillE == false){
            countE++;
            if(countE > 500){
                readySkillE = true;
                countE = 0;
            }
        }
    }
    public void cooldownQ(){
        if(readySkillQ == false){
            counterQ++;
            if(counterQ > 100 ){
                readySkillQ = true;
                counterQ = 0;
            }
        }
    }
    public void cooldownR(){
        if(readySkillR == false){
            counterR++;
            if(counterR > 200){
                readySkillR = true;
                counterR =0;
            }
        }
    }

    public void randomSkill(){
        Random random = new Random();
        int rand = random.nextInt(100);
        if(rand<30){
            randomQ =true;
            randomR = false;
        }
        else{
            randomQ = false;
            randomR = true;
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
                if (checkAtack.intersects(playerArea) && !attackQ && readySkillR) {
                    attacking = true;
                    readySkillR = false;
                }
                cooldownR();
                break;
            case "down":
                checkAtack.x = posX + panel.tileSize/2;
                checkAtack.y = posY + panel.tileSize * 3;
                checkAtack.width = panel.tileSize*2;
                checkAtack.height = panel.tileSize*1;
                if (checkAtack.intersects(playerArea) && !attackQ && readySkillR) {
                    attacking = true;
                    readySkillR = false;
                }
                cooldownR();
                break;
            case "left":
                checkAtack.x = posX - panel.tileSize;
                checkAtack.y = posY + panel.tileSize/2;
                checkAtack.width = panel.tileSize * 1;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea) && !attackQ && readySkillR) {
                    attacking = true;
                    readySkillR = false;
                }
                cooldownR();
                break;
            case "right":
                checkAtack.x = posX + panel.tileSize *3;
                checkAtack.y = posY + panel.tileSize/2;
                checkAtack.width = panel.tileSize*1;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea) && !attackQ && readySkillR) {
                    attacking = true;
                    readySkillR = false;
                }
                cooldownR();
                break;
        }
    }
    public void checkAttackingQ() {
        Rectangle checkAtack = new Rectangle(0,0,panel.tileSize,panel.tileSize);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(),panel.getPlayer().getPosY(),panel.tileSize, panel.tileSize);
        switch (direction) {
            case "up":
                checkAtack.x = posX + panel.tileSize;
                checkAtack.y = posY - panel.tileSize;
                checkAtack.width = panel.tileSize*2;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea) && !attacking && readySkillQ) {
                    attackQ = true;
                    readySkillQ = false;
                }
                cooldownQ();
                break;
            case "down":
                checkAtack.x = posX + panel.tileSize;
                checkAtack.y = posY + panel.tileSize * 3;
                checkAtack.width = panel.tileSize*2;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea) && !attacking && readySkillQ) {
                    attackQ = true;
                    readySkillQ = false;
                }
                cooldownQ();
                break;
            case "left":
                checkAtack.x = posX - panel.tileSize;
                checkAtack.y = posY + panel.tileSize;
                checkAtack.width = panel.tileSize * 2;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea) && !attacking && readySkillQ) {
                    attackQ = true;
                    readySkillQ = false;
                }
                cooldownQ();
                break;
            case "right":
                checkAtack.x = posX + panel.tileSize *3;
                checkAtack.y = posY + panel.tileSize;
                checkAtack.width = panel.tileSize*2;
                checkAtack.height = panel.tileSize*2;
                if (checkAtack.intersects(playerArea) && !attacking && readySkillQ) {
                    attackQ = true;
                    readySkillQ = false;
                }
                cooldownQ();
                break;
        }
    }
    public void checkHitBox() {

        if(!attacking && !readySkillR && !randomR) return;
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

    public void checkHitBoxQ(){
        if(!attackQ&& !readySkillQ && !readySkillQ) return;
        if(spriteTickQSke < 3) return;
        Rectangle playerHitBoxArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y,
                panel.getPlayer().getHitBoxArea().width,
                panel.getPlayer().getHitBoxArea().height);

        Rectangle attackArea = new Rectangle(0,0,0,0);
        switch (direction){
            case "up":
                attackArea.x = posX + panel.tileSize /2 ;
                attackArea.y = posY - panel.tileSize *3;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "down":
                attackArea.x = posX + panel.tileSize/2;
                attackArea.y = posY + panel.tileSize * 4;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                attackArea.x = posX + panel.tileSize *4;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                attackArea.x = posX - panel.tileSize*3;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize * 3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }
}