package entity.monsters;

import entity.Monster;
import main.Panel;
import map.SpiderCave;

import java.awt.*;
import java.util.Random;

public class Spider extends Monster {

    SpiderCave cave;
    public Spider(Panel panel, int speed, int skillThread, SpiderCave spiderCave) {
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

        this.monsterSize = 1;
        this.cave = spiderCave;

        this.triggerArea = new Rectangle(-2 * panel.tileSize, -2 * panel.tileSize, 5 * panel.tileSize, 5 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4);
        this.hitBoxArea = new Rectangle(0, 0, panel.tileSize, panel.tileSize);
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY
        ) {
            panel.getMonsterAsset().getSpiderAssets().draw(this, screenX, screenY, spriteIndex, g2);
        }
    }

    public void setRandomDirection() {

        do {
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
        } while (abandonCave());
    }

    boolean abandonCave() {
        switch (direction) {
            case "left":
                return posX - speed < cave.getPosX() + cave.getTerritoryArea().x;
            case "right":
                return posX + speed > cave.getPosX() + cave.getTerritoryArea().x + cave.getTerritoryArea().width;
            case "top":
                return posY - speed < cave.getPosY() + cave.getTerritoryArea().y;
            case "bottom":
                return posY + speed > cave.getPosY() + cave.getTerritoryArea().y + cave.getTerritoryArea().height;
        }
        return false;
    }

    public void setAction() {
        if (++actionLockCounter == 50) {
            actionLockCounter = 0;

            checkTriggerPlayer();
            if (triggering) {

                int distanceX = posX - panel.getPlayer().getPosX();
                int distanceY = posY - panel.getPlayer().getPosY();

                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    collisionDetected = false;
                    direction = distanceY < 0 ? "down" : "up";
                    panel.collisionHandler.checkMapCollision(this);
                    if (!collisionDetected && !abandonCave())
                        return;
                } else {
                    collisionDetected = false;
                    direction = distanceX < 0 ? "right" : "left";
                    panel.collisionHandler.checkMapCollision(this);
                    if (!collisionDetected && !abandonCave())
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

    public void checkHitBox() {
        Rectangle playerHitBoxArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY() + panel.getPlayer().getHitBoxArea().y,
                panel.getPlayer().getHitBoxArea().width,
                panel.getPlayer().getHitBoxArea().height);

        Rectangle attackArea = new Rectangle(posX,posY,panel.tileSize/2,panel.tileSize/2);
        if(attackArea.intersects(playerHitBoxArea)) {
            System.out.println("Spider attack");
            panel.setEffect(panel.getPlayer(), "poison", 5, 2);

            panel.getPlayer().damage(1);
        }
    }
}