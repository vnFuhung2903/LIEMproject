package entity;

import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Skill extends Entity {

    Entity user;
    protected Skill(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
    }

    public void setSkill(int posX, int posY, String direction, boolean alive, Entity user) {
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;

    }

    public void update() {

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
        skillThread--;
        if (skillThread <= 0) {
            alive = false;
            skillThread = 40;
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage currentFrameImg = null;
        int screenX = posX - panel.getPlayer().posX + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().posY + panel.getPlayer().screenY;

        if (posX + panel.tileSize > panel.getPlayer().posX - panel.getPlayer().screenX &&
                posX - panel.tileSize < panel.getPlayer().posX + panel.getPlayer().screenX &&
                posY + panel.tileSize > panel.getPlayer().posY - panel.getPlayer().screenY &&
                posY - panel.tileSize < panel.getPlayer().posY + panel.getPlayer().screenY
        ) {
            switch (direction) {
                case "up":
                    currentFrameImg = skillUp;

                    break;
                case "down":
                    currentFrameImg = skillDown;

                    break;
                case "left":
                    currentFrameImg = skillLeft;

                    break;
                case "right":
                    currentFrameImg = skillRight;

                    break;

            }
                g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 2, null);
                System.out.println(posX + posY);
                System.out.println("pew pew");
        }
    }
}