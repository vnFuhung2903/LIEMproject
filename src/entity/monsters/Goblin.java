package entity.monsters;

import entity.Monster;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Goblin extends Monster {
    public Goblin(Panel panel, int speed, int skillThread) {
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
        this.attackInterval = 10;

        this.triggerArea = new Rectangle(-4 * panel.tileSize, -4 * panel.tileSize, 9 * panel.tileSize, 9 * panel.tileSize);
        this.collisionArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, 0, panel.tileSize / 2);
        this.hitBoxArea = new Rectangle(panel.tileSize / 2, panel.tileSize / 2, panel.tileSize, panel.tileSize);
        getMonsterImage();
    }

    public void getMonsterImage() {

        try {
            moveUp = new BufferedImage[3];
            moveDown = new BufferedImage[3];
            moveLeft = new BufferedImage[3];
            moveRight = new BufferedImage[3];

            attackUp = new BufferedImage[6];
            attackDown = new BufferedImage[6];
            attackLeft = new BufferedImage[6];
            attackRight = new BufferedImage[6];

            for (int i = 0; i < 3;i++) {

                String fileMoveUp = "assets/goblin/goblinMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/goblin/goblinMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/goblin/goblinMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/goblin/goblinMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            for (int i = 0; i < 6;i++) {

                String fileAttackUp = "assets/goblin/goblinAttackUp-0" + (i + 1) +".png";
                attackUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/goblin/goblinAttackDown-0" + (i + 1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/goblin/goblinAttackLeft-0" + (i + 1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/goblin/goblinAttackRight-0" + (i + 1) +".png";
                attackRight[i] = ImageIO.read(new File(fileAttackRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage currentFrameImg = null;

        int screenX = posX - panel.getPlayer().getPosX() + panel.getPlayer().screenX;
        int screenY = posY - panel.getPlayer().getPosY() + panel.getPlayer().screenY;

        if (posX + panel.tileSize >= panel.getPlayer().getPosX() - panel.getPlayer().screenX &&
                posX - panel.tileSize <= panel.getPlayer().getPosX() + panel.getPlayer().screenX &&
                posY + panel.tileSize >= panel.getPlayer().getPosY() - panel.getPlayer().screenY &&
                posY - panel.tileSize <= panel.getPlayer().getPosY() + panel.getPlayer().screenY
        ) {

            if(attacking) {
                switch (direction) {
                    case "up":
                        currentFrameImg = attackUp[attackIndex];
                        break;
                    case "down":
                        currentFrameImg = attackDown[attackIndex];
                        break;
                    case "left":
                        currentFrameImg = attackLeft[attackIndex];
                        break;
                    case "right":
                        currentFrameImg = attackRight[attackIndex];
                        break;
                }

                g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * monsterSize * 3 / 2, panel.tileSize * monsterSize * 3 / 2, null);
                return;
            }

            switch (direction) {
                case "up":
                    currentFrameImg = moveUp[spriteIndex];
                    break;
                case "down":
                    currentFrameImg = moveDown[spriteIndex];
                    break;
                case "left":
                    currentFrameImg = moveLeft[spriteIndex];
                    break;
                case "right":
                    currentFrameImg = moveRight[spriteIndex];
                    break;
            }

            g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * monsterSize * 3 / 2, panel.tileSize * monsterSize * 3 / 2, null);
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

        if(posX >= panel.getPlayer().getPosX() && posX <= panel.getPlayer().getPosX() + panel.tileSize && posY >= panel.getPlayer().getPosY() - panel.tileSize / 2 && posY <= panel.getPlayer().getPosY() + panel.tileSize / 2) {
            System.out.println("goblin attack");
            panel.getPlayer().damage(1);
        }

        if(posX <= panel.getPlayer().getPosX() && posX >= panel.getPlayer().getPosX() - panel.tileSize && posY >= panel.getPlayer().getPosY() - panel.tileSize / 2 && posY <= panel.getPlayer().getPosY() + panel.tileSize / 2) {
            System.out.println("goblin attack");
            panel.getPlayer().damage(1);
        }

        if(posX >= panel.getPlayer().getPosX() - panel.tileSize / 2 && posX <= panel.getPlayer().getPosX() + panel.tileSize / 2 && posY <= panel.getPlayer().getPosY() && posY >= panel.getPlayer().getPosY() - panel.tileSize) {
            System.out.println("goblin attack");
            panel.getPlayer().damage(1);
        }

        if(posX >= panel.getPlayer().getPosX() - panel.tileSize / 2 && posX <= panel.getPlayer().getPosX() + panel.tileSize / 2 && posY >= panel.getPlayer().getPosY() && posY <= panel.getPlayer().getPosY() + panel.tileSize) {
            System.out.println("goblin attack");
            panel.getPlayer().damage(1);
        }
    }
}