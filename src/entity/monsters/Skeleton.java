package entity.monsters;
import entity.Monster;
import entity.skills.skeleton.Slash;
import main.Panel;

import java.awt.*;
import java.util.Random;

public class Skeleton extends Monster {
    
    boolean invincible = false;
    protected int counterQ = 0, counterR = 0, counterInvincible = 0, invincibleCD, attacking;


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
        readySkillQ = true;
        readySkillR = true;
        attacking = 0;
        maxHp = hp;

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
            if(invincible) {
                panel.getMonsterAsset().getSkeletonAssets().drawE(this,screenX,screenY,spriteIndex,true,g2);
                return;
            }

            if ((attacking & 1) == 1) {
                panel.getMonsterAsset().getSkeletonAssets().draw(this,screenX,screenY,attackIndex,true,g2);
                return;
            }

            if((attacking & 2) == 2){
                panel.getMonsterAsset().getSkeletonAssets().drawQ(this,screenX,screenY,spriteTickQSke,true,g2);
                if(spriteTickQSke > 4) panel.getMonsterAsset().getSkeletonAssets().drawQEffect(this,screenX,screenY,attackQIndex,g2);
                return;
            }

            panel.getMonsterAsset().getSkeletonAssets().draw(this,screenX,screenY,spriteIndex,false,g2);
        }

    }

    public void setAction() {

        checkTriggerPlayer();
        if (++actionLockCounter == 50) {
            actionLockCounter = 0;

            if (!invincible && triggering) {

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
    public void update() {
        cooldownInvincible();
        cooldownQ();
        cooldownR();
        if(readySkillR && spriteTickQSke == 0 && counterInvincible == 0) checkAttacking();
        if(readySkillQ && attackIndex == 0 && counterInvincible == 0) checkAttackingQ();
        while(attacking == 3 || checkInvincible()) {
            Random random = new Random();
            int r = random.nextInt(1000);
            switch (r % 4) {
                case 0:
                    if (checkInvincible()) {
                        invincible = true;
                        attacking = 0;
                        break;
                    }
                case 1:
                    if((attacking & 2) == 2) {
                        attacking = 2;
                        break;
                    }
                case 2:
                    if((attacking & 1) == 1) {
                        attacking = 1;
                        break;
                    }
                default:
                    attacking = 0;
            }
        }

        if(invincible) {
            speed = 15;
            hitBoxArea.width = 0;
            hitBoxArea.height = 0;
            updateSprite();
            updateInvincibleTime();
            move();
            return;
        }

        if(attacking == 2) {
            readySkillQ = false;
            updateAttackAnimationQ();
            if(spriteTickQSke > 4) updateAttackAnimationQEffect();
            checkHitBoxQ();
            return;
        }

        if(attacking == 1) {
            readySkillR = false;
            updateAttackAnimation();
            checkHitBox();
            return;
        }
        move();
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
                spriteTickQSke = 0;
                attackQIndex = 0;
                attacking = 0;
            }
            skillThread = 10;
        }
    }
    public void updateAttackAnimationQ() {
        if(++attackTickQ >= attackInterval * 3 / 2) {
            attackTickQ = 0;
            if(++spriteTickQSke >= 6){
                spriteTickQSke = 5;
            }
        }
    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex >= 6) {
                attackIndex = 0;
                attacking = 0;
            }

            else if(attackIndex == 5) {
                Slash slash = new Slash(panel, 10, this);
                panel.setSkill(slash);
            }
        }
    }

    public boolean checkInvincible() {
        return hp <= maxHp * 30 / 100 && !invincible && readySkillE;
    }

    public void updateInvincibleTime() {
        if(++counterInvincible >= 150) {
            hp += 30 * hp / 100;
            counterInvincible = 0;
            invincible = false;
            readySkillE = false;
            speed = 5;
            hitBoxArea.width = panel.tileSize * 4;
            hitBoxArea.height = panel.tileSize *4;
        }
    }

    public void cooldownQ() {
        if(!readySkillQ && (attacking & 2) == 0){
            if(++counterQ > 200) {
                readySkillQ = true;
                System.out.println("cdQ");
                counterQ = 0;
            }
        }
    }
    public void cooldownR(){
        if(!readySkillR && (attacking & 1) == 0) {
            if(++counterR > 200) {
                readySkillR = true;
                System.out.println("cdR");
                counterR = 0;
            }
        }
    }

    public void cooldownInvincible(){
        if(!readySkillE && !invincible) {
            if(--invincibleCD <= 0) {
                readySkillE = true;
                System.out.println("cdI");
                invincibleCD = 500;
            }
        }
    }

    public void checkAttacking() {
        Rectangle checkAttacking = new Rectangle(0, 0, panel.tileSize, panel.tileSize);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(), panel.getPlayer().getPosY(), panel.tileSize, panel.tileSize);
        switch (direction) {
            case "up":
                checkAttacking.x = posX + panel.tileSize / 2;
                checkAttacking.y = posY - panel.tileSize;
                checkAttacking.width = panel.tileSize * 2;
                checkAttacking.height = panel.tileSize;
                if (checkAttacking.intersects(playerArea) && (attacking & 1) == 0 && counterR == 0) {
                    attacking |= 1;
                }
                break;
            case "down":
                checkAttacking.x = posX + panel.tileSize / 2;
                checkAttacking.y = posY + panel.tileSize * 3;
                checkAttacking.width = panel.tileSize * 2;
                checkAttacking.height = panel.tileSize;
                if (checkAttacking.intersects(playerArea) && (attacking & 1) == 0 && counterR == 0) {
                    attacking |= 1;
                }
                break;
            case "left":
                checkAttacking.x = posX - panel.tileSize;
                checkAttacking.y = posY + panel.tileSize / 2;
                checkAttacking.width = panel.tileSize;
                checkAttacking.height = panel.tileSize * 2;
                if (checkAttacking.intersects(playerArea) && (attacking & 1) == 0 && counterR == 0) {
                    attacking |= 1;
                }
                break;
            case "right":
                checkAttacking.x = posX + panel.tileSize * 3;
                checkAttacking.y = posY + panel.tileSize / 2;
                checkAttacking.width = panel.tileSize;
                checkAttacking.height = panel.tileSize * 2;
                if (checkAttacking.intersects(playerArea) && (attacking & 1) == 0 && counterR == 0) {
                    attacking |= 1;
                }
                break;
        }
    }

    public void checkAttackingQ() {
        Rectangle checkAttacking = new Rectangle(0,0,panel.tileSize, panel.tileSize);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(),panel.getPlayer().getPosY(),panel.tileSize, panel.tileSize);
        switch (direction) {
            case "up":
                checkAttacking.x = posX + panel.tileSize;
                checkAttacking.y = posY - panel.tileSize;
                checkAttacking.width = panel.tileSize * 2;
                checkAttacking.height = panel.tileSize * 2;
                if (checkAttacking.intersects(playerArea) && (attacking & 2) == 0 && counterQ == 0) {
                    attacking |= 2;
                }
                break;
            case "down":
                checkAttacking.x = posX + panel.tileSize;
                checkAttacking.y = posY + panel.tileSize * 3;
                checkAttacking.width = panel.tileSize * 2;
                checkAttacking.height = panel.tileSize * 2;
                if (checkAttacking.intersects(playerArea) && (attacking & 2) == 0 && counterQ == 0) {
                    attacking |= 2;
                }
                break;
            case "left":
                checkAttacking.x = posX - panel.tileSize;
                checkAttacking.y = posY + panel.tileSize;
                checkAttacking.width = panel.tileSize * 2;
                checkAttacking.height = panel.tileSize * 2;
                if (checkAttacking.intersects(playerArea) && (attacking & 2) == 0 && counterQ == 0) {
                    attacking |= 2;
                }
                break;
            case "right":
                checkAttacking.x = posX + panel.tileSize *3;
                checkAttacking.y = posY + panel.tileSize;
                checkAttacking.width = panel.tileSize * 2;
                checkAttacking.height = panel.tileSize * 2;
                if (checkAttacking.intersects(playerArea) && (attacking & 2) == 0 && counterQ == 0) {
                    attacking |= 2;
                }
                break;
        }
    }
    public void checkHitBox() {

        if((attacking & 1) == 0) return;
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
                    panel.getPlayer().damage(1);
                }
                break;
            case "down":
                attackArea.x = posX + panel.tileSize / 2;
                attackArea.y = posY + panel.tileSize * 3;
                attackArea.width = panel.tileSize * 3;
                attackArea.height = panel.tileSize * 2;
                if(attackArea.intersects(playerHitBoxArea)){
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                attackArea.x = posX + panel.tileSize * 3;
                attackArea.y = posY + panel.tileSize / 2;
                attackArea.width = panel.tileSize * 2;
                attackArea.height = panel.tileSize * 3;
                if(attackArea.intersects(playerHitBoxArea)){
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                attackArea.x = posX - panel.tileSize;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize * 2;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }

    public void checkHitBoxQ(){
        if((attacking & 2) == 0) return;
        if(spriteTickQSke < 3) return;
        
        Rectangle playerHitBoxArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y,
                panel.getPlayer().getHitBoxArea().width,
                panel.getPlayer().getHitBoxArea().height);

        Rectangle attackArea = new Rectangle(0,0,0,0);
        switch (direction){
            case "up":
                attackArea.x = posX + panel.tileSize / 2 ;
                attackArea.y = posY - panel.tileSize * 3;
                attackArea.width = panel.tileSize * 3;
                attackArea.height = panel.tileSize * 3;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "down":
                attackArea.x = posX + panel.tileSize/2;
                attackArea.y = posY + panel.tileSize * 4;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                attackArea.x = posX + panel.tileSize *4;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize*3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                attackArea.x = posX - panel.tileSize*3;
                attackArea.y = posY + panel.tileSize/2;
                attackArea.width = panel.tileSize * 3;
                attackArea.height = panel.tileSize*3;
                if(attackArea.intersects(playerHitBoxArea)){
//                    System.out.println("SkeletonQ attack");
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }
}