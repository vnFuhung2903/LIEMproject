package entity.monsters;
import entity.Effect;
import entity.Monster;
import main.Panel;
import java.awt.*;
import java.util.Random;

public class Ghost extends Monster {

    boolean invisible;
    int counterE = 0, counterInvisible = 0, invisibleCD, passiveCD, action,timeCurse;
    public Ghost(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
        this.hp = 10;
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
        this.hp = 30000;
        this.maxHp = 30000;

        this.triggerArea = new Rectangle(-4 * panel.tileSize, -4 * panel.tileSize, 9 * panel.tileSize, 9 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize, panel.tileSize, panel.tileSize * 4, panel.tileSize * 4);

    }

    public void draw(Graphics2D g2) {

        if (invisible)
            return;

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (onScreen(4,screenX,screenY)) {
            if (attacking) {
                panel.getAsset().getGhostAssets().draw1(this, screenX, screenY, attackIndex, true,g2);
            }
            panel.getAsset().getGhostAssets().draw1(this, screenX, screenY, spriteIndex, false, g2);
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
            if (++spriteIndex >= 6) spriteIndex = 0;
            spriteTick = 0;
        }
    }

    public void updateAttackAnimation() {
        if (++attackTick >= attackInterval) {
            attackTick = 0;
            if (++attackIndex >= 6) {
                attackIndex = 0;
                attacking = false;
                action = 0;
            }
        }
    }
    public void cooldownE(){
        if(!readySkillE) {
            if (++counterE > 10000) {
                readySkillE = true;
                counterE = 0;
            }
        }
    }

    @Override
    public void update() {
        cooldownInvisible();
        cooldownE();
        cooldownPassive();
        if(readySkillE && counterInvisible == 0 && counterE == 0) attacking = true;
        if(readySkillQ && counterInvisible == 0 && passiveCD == 0) attacking = true;
        if(action == 0 && !invisible) {
            Random random = new Random();
            int r = random.nextInt(1000);
            switch (r % 4) {
                case 0:
                    if (checkInvisible()) {
                        invisible = true;
                        action = 0;
                        break;
                    }
                case 1:
                    if (attacking && readySkillE) {
                        action = 1;
                        break;
                    }
                case 2:
                    if (attacking && readySkillQ) {
                        action = 2;
                        break;
                    }
                default:
                    action = 0;
            }
        }


        if(invisible) {
            System.out.println("Invisible");
            speed = 15;
            updateSprite();
            updateInvisibleTime();
            move();
            return;
        }

        if(action == 1) {
            System.out.println("Spawning");
            readySkillE = false;
            updateAttackAnimation();
            if (attackIndex == 5 && attackTick == 0) {
                usingE();
            }
            return;
        }

        if(action == 2) {
            System.out.println("Cursing");
            readySkillQ = false;
            updateAttackAnimation();
            if (attackIndex == 5 && attackTick == 0) {
                panel.setEffect(panel.getPlayer(), "ghostPassive", 3, 2);
                if(--timeCurse < 0) {
                    timeCurse = 10;
                    curse();

                }
            }
            return;
        }
        move();
    }

//    public void checkAttacking() {
//        Rectangle checkAttacking = new Rectangle(posX - panel.tileSize / 2,posY -panel.tileSize/2,panel.tileSize*5,panel.tileSize*5);
//        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(),panel.getPlayer().getPosY(),panel.tileSize, panel.tileSize);
//        if(checkAttacking.intersects(playerArea) && !attacking && counterE == 0) {
//            attacking = true;
//        }
//    }

    public boolean checkInvisible() {
        return !invisible && readySkillR;
    }

    public void updateInvisibleTime() {
        if(++counterInvisible >= 150) {
            counterInvisible = 0;
            invisible = false;
            readySkillR = false;
            speed = 10;
        }
    }

    public void cooldownInvisible(){
        if(!readySkillR && !invisible) {
            if(++invisibleCD >= 500) {
                readySkillR = true;
                System.out.println("cdI");
                invisibleCD = 0;
            }
        }
    }

    public void curse() {

        Random random = new Random();
        int r = random.nextInt();
        switch (r % 3) {
            case 0:
                panel.setEffect(panel.getPlayer(), "ice", 10, 2);
                break;
            case 1:
                panel.setEffect(panel.getPlayer(), "burn", 10, 2);
                break;
            default:
                panel.setEffect(panel.getPlayer(), "poison", 10, 2);
                break;
        }
    }
    public void cooldownPassive() {
        if(!readySkillQ) {
            if(--passiveCD <= 0) {
                readySkillQ = true;
                System.out.println("cdQ");
                passiveCD = 500;
            }
        }
    }

    public void usingE() {
        int x = posX;
        int y = posY;
//        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
//        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;
        switch (direction) {
            case "up":
                y -= panel.tileSize * 2;
                break;
            case "down":
                y += panel.tileSize * 6;
                break;
            case "left":
                x -= panel.tileSize * 2;
                break;
            case "right":
                x += panel.tileSize * 6;
                break;
        }

        for (int i = 0; i < 4; i++) {
            panel.spawnSlave(x + i * panel.tileSize, y);
        }
    }
}