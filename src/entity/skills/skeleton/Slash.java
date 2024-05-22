package entity.skills.skeleton;

import entity.Entity;
import entity.Monster;
import entity.Skill;
import main.Panel;

import java.awt.*;

public class Slash extends Skill {
    public Slash(Panel panel, int speed, Entity entity) {
        super(panel, speed, 30, entity);
        direction = entity.getDirection();
        casted = true;
        switch (direction) {
            case "up":
                posX = entity.getPosX();
                posY = entity.getPosY() - panel.tileSize * 3;
                break;
            case "down":
                posX = entity.getPosX();
                posY = entity.getPosY() + panel.tileSize * 3;
                break;
            case "left":
                posX = entity.getPosX() - panel.tileSize * 3;
                posY = entity.getPosY();
                break;
            case "right":
                posX = entity.getPosX() + panel.tileSize * 3;
                posY = entity.getPosY();
                break;
        }
        System.out.println("Create a slash");
    }

    public void update() {
        if(--skillThread == 0)
            casted = false;
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
        checkHitBox();
    }

    public void checkHitBox() {
        Rectangle hitboxArea = new Rectangle(posX,posY,0,0);
        Rectangle playerArea = new Rectangle(panel.getPlayer().getPosX() + panel.getPlayer().getHitBoxArea().x,
                panel.getPlayer().getPosY()+panel.getPlayer().getHitBoxArea().y,
                panel.tileSize,panel.tileSize);
        switch (getDirection()){
            case "up" :
                hitboxArea.y = posY + panel.tileSize;
                hitboxArea.width = panel.tileSize * 4;
                hitboxArea.height = panel.tileSize * 2;
                if(hitboxArea.intersects(playerArea)){
                    System.out.println("splash attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case  "down":
                hitboxArea.y = posY + panel.tileSize;
                hitboxArea.width = panel.tileSize * 4;
                hitboxArea.height = panel.tileSize * 2;
                if(hitboxArea.intersects(playerArea)){
                    System.out.println("splash attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "left":
                hitboxArea.x = posX + panel.tileSize;
                hitboxArea.width = panel.tileSize * 2;
                hitboxArea.height = panel.tileSize * 4;
                if(hitboxArea.intersects(playerArea)){
                    System.out.println("splash attack");
                    panel.getPlayer().damage(1);
                }
                break;
            case "right":
                hitboxArea.x = posX + panel.tileSize;
                hitboxArea.width = panel.tileSize * 2;
                hitboxArea.height = panel.tileSize * 4;
                if(hitboxArea.intersects(playerArea)){
                    System.out.println("splash attack");
                    panel.getPlayer().damage(1);
                }
                break;
        }
    }
    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;
        if (onScreen(4,screenX,screenY))
            panel.getMonsterAsset().getSkeletonSlashAssets().draw(screenX, screenY, direction, g2);
    }
}
