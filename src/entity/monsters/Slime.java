package entity.monsters;

import entity.Monster;
import main.Panel;

import java.awt.*;
import java.util.Random;

public class Slime extends Monster {

    String color;
    public Slime(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
        this.name = "Slime";
        this.hp = 10;
        Random randomColor = new Random();
        int colorIndex = randomColor.nextInt(1000);
        switch (colorIndex % 4) {
            case 0:
                color = "Red";
                break;
            case 1:
                color = "Green";
                break;
            case 2:
                color = "Poison";
                break;
            default:
                color = "Blue";
                break;
        }
        setRandomDirection();

        this.monsterSize = 1;
        this.triggerArea = null;
        this.hp = 100;
        this.collisionArea = new Rectangle(panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4, panel.tileSize / 4);
        this.hitBoxArea = new Rectangle(0, 0, panel.tileSize, panel.tileSize);
    }

    public void draw(Graphics2D g2) {

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (onScreen(1,screenX,screenY)) {
            panel.getAsset().getSlimeAssets().draw(this, screenX, screenY, spriteIndex, g2);
        }
    }

    public void setAction() {
        if(++actionLockCounter == 50) {
            actionLockCounter = 0;
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

        Rectangle attackArea = new Rectangle(posX,posY,panel.tileSize,panel.tileSize);
        if(attackArea.intersects(playerHitBoxArea)) {
            switch (color) {
                case "Red":
                    panel.setEffect(panel.getPlayer(), "burn", 5, 2);
                    break;
                case "Green":
                    panel.setEffect(panel.getPlayer(), "healing", 5, 2);
                    break;
                case "Blue":
                    panel.setEffect(panel.getPlayer(), "ice", 5, 2);
                    break;
                case "Poison":
                    panel.setEffect(panel.getPlayer(), "poison", 5, 2);
                    break;
                default :
                    System.out.println("ko co gi");
            }
        }
    }

    public String getColor() {
        return color;
    }
}