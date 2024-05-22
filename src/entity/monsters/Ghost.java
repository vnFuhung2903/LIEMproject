package entity.monsters;
import entity.Monster;
import main.Panel;
import java.awt.*;
import java.util.Random;

public class Ghost extends Monster {

    int counterE = 0;
    public Ghost(Panel panel, int speed, int skillThread) {
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

        if (onScreen(4,screenX,screenY)) {
            if (attacking && readySkillE) {
                panel.getMonsterAsset().getGhostAssets().draw1(this, screenX, screenY, attackIndex, true,g2);
//                usingE();

                readySkillE = false;
            }
            cooldownE();
            panel.getMonsterAsset().getGhostAssets().draw1(this, screenX, screenY, spriteIndex, false, g2);
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
            }
        }
    }
    public void cooldownE(){
        if(readySkillE == false) {
            counterE++;
            if (counterE > 1000) {
                readySkillE = true;
                counterE = 0;
            }
        }
    }
    @Override
    public void update(){
        checkAttacking();
        checkHitBox();
        if(attacking) {
            updateAttackAnimation();
            move();
        }else{
            move();
        }
    }

    public void checkAttacking() {
        Rectangle checkAttacking = new Rectangle(posX - panel.tileSize / 2,posY -panel.tileSize/2,panel.tileSize*5,panel.tileSize*5);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX(),panel.getPlayer().getPosY(),panel.tileSize, panel.tileSize);
        if(checkAttacking.intersects(playerArea)) {
            attacking = true;
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
                for (int i = 0; i < 4; i++) {
                    panel.setMonstersGhost(x + i * panel.tileSize, y);
                }

                break;
            case "down":
                y += panel.tileSize * 6;
                for (int i = 0; i < 4; i++) {
                    panel.setMonstersGhost(x + i * panel.tileSize, y);
                }
                break;
            case "left":
                x -= panel.tileSize * 2;
                for (int i = 0; i < 4; i++) {
                    panel.setMonstersGhost(x, y + i * panel.tileSize );
                }
                break;
            case "right":
                x += panel.tileSize * 6;
                for (int i = 0; i < 4; i++) {
                    panel.setMonstersGhost(x, y + i * panel.tileSize);
                }
                break;
        }
    }
}