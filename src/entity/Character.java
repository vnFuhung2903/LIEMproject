package entity;

import main.*;
import main.Panel;
import skill.SkillOneForKnight;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character extends Entity {
    KeyHandler keyHandler;
    MouseEventHandler mouseHandler;
    final public int screenX;
    final public int screenY;
    int attackIndex,attackTick, attackSpeed = 10;

    public Character(Panel panel, int speed, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {

        super(panel, speed, skillThread);
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseEventHandler;
        this.attacking = false;

        this.collisionArea = new Rectangle(0, panel.tileSize, panel.tileSize, panel.tileSize);

        // set vi tri cua map so voi man hinh
        posX = panel.tileSize * 11; // posX = 480
        posY = panel.tileSize * 10; // posY =480

        // set vi tri nhan vat so voi man hinh
        this.screenX = panel.screenWidth/2 - panel.tileSize; // screenX = 480
        this.screenY = panel.screenHeight/2 - panel.tileSize; // screenY = 276

        getPlayerImage();
        setDefaultValues();
        getPlayerAttackImage();
    }

    public void getPlayerImage() {

        try {
            moveUp = new BufferedImage[6];
            moveDown = new BufferedImage[6];
            moveLeft = new BufferedImage[6];
            moveRight = new BufferedImage[6];
            attackUp = new BufferedImage[8];
            attackDown = new BufferedImage[8];
            attackLeft = new BufferedImage[8];
            attackRight = new BufferedImage[8];

            //get Move
            int move = 5;
            for (int i =0; i< move;i++) {

                String fileMoveUp = "assets/knight/knightMoveUp-0" + (i+1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/knight/knightMoveDown-0" + (i+1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/knight/knightMoveLeft-0" + (i+1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/knight/knightMoveRight-0" + (i+1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPlayerAttackImage() {
        try {
            attackUp = new BufferedImage[8];
            attackDown = new BufferedImage[8];
            attackLeft = new BufferedImage[8];
            attackRight = new BufferedImage[8];

            int attack = 8;

            for (int i =0; i< attack;i++) {

                String fileMoveUp = "assets/knight/knightPunchUp-0" + (i+1) +".png";
                attackUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/knight/knightPunchDown-0" + (i+1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/knight/knightPunchLeft-0" + (i+1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/knight/knightPunchRight-0" + (i+1) +".png";
                attackRight[i] = ImageIO.read(new File(fileMoveRight));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        direction = "down";
        skill = new SkillOneForKnight(panel, 10, 30);
    }

    public void update() {
        checkFlash();
        if (attacking) {
            updateAnimationAttack();
            moveAnimation();
            checkSkill();
            if(checkSkill&& skill.alive && attackIndex == 6) {
                skill.setSkill(posX, posY, direction, true, this);

//        // ADD skill to The list
                panel.skillList.add(skill);
            }

        } else if(flash) {
            moveFlashAnimation();
            moveAnimation();
        }
        else moveAnimation();
    }

    public void moveAnimation() {
        if (keyHandler.isMoving()) {
            if (keyHandler.upPressed){
                direction = "up";
            }
            if (keyHandler.downPressed){
                direction = "down";
            }
            if (keyHandler.leftPressed){
                direction = "left";
            }
            if (keyHandler.rightPressed){
                direction = "right";
            }

            // Check collision
            collisionDetected = false;
            panel.collisionHandler.checkTileCollision(this);
            if(!collisionDetected) {
                switch (direction) {
                    case "left":
                        posX -= speed;
                        break;
                    case "right":
                        posX += speed;
                        break;
                    case "up":
                        posY -= speed;
                        break;
                    case "down":
                        posY += speed;
                        break;
                }
            }

            updateSprite();
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum > 4) spriteNum = 1;
            else spriteNum ++;
            spriteCounter = 0;
        }
    }

    public void moveFlashAnimation() {
        if (keyHandler.flashPressed) {
            speed = 10;
            spriteCounter++;
            if (spriteCounter > 5) {
                if (spriteNum > 4) spriteNum = 1;
                else spriteNum ++;
                spriteCounter = 0;
                flash = false;
                speed = 2;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage currentFrameImg = null;

        checkAttacking();

        if (attacking) {
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

            g2.drawImage(currentFrameImg, screenX, screenY, panel.characterSize, panel.characterSize, null);
            System.out.println("attacking");
        }
        
        else
        {
            switch (direction) {
                case "up":
                    currentFrameImg = moveUp[spriteNum - 1];
                    break;
                case "down":
                    currentFrameImg = moveDown[spriteNum - 1];
                    break;
                case "left":
                    currentFrameImg = moveLeft[spriteNum - 1];
                    break;
                case "right":
                    currentFrameImg = moveRight[spriteNum - 1];
                    break;
            }

            g2.drawImage(currentFrameImg, screenX, screenY, panel.characterSize, panel.characterSize, null);
        }
    }
    public void updateAnimationAttack() {
        attackTick++;
        if (attackTick >= attackSpeed) {
            attackTick = 0;
            attackIndex ++;
            System.out.println(attackIndex);
            if (attackIndex > 7 ) {
                attackIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkAttacking() {
        if(mouseHandler.isLeftClicked() || mouseHandler.isRightClicked()) {
            attacking = true;
        }
    }

    public void checkFlash() {
        if(keyHandler.flashPressed) {
            speed = 1000;
            flash = true;
        } else {
            speed = 2;         }
    }
    public void checkSkill(){
        if(keyHandler.isUsingSkill() && skill.alive) {
//            skill.setSkill(posX, posY, direction, true, this);
//
//            // ADD skill to The list
//            panel.skillList.add(skill);
            checkSkill = true;
        }

    }
}
