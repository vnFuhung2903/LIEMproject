package entity;

import main.Panel;
import main.KeyHandler;
import skill.SkillOneForKnight;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Character extends Entity {
    KeyHandler keyH;
    final public int screenX;
    final public int screenY;
    int punchIndex,punchTick, punchSpeed = 10;

    public Character(Panel panel, int speed, int skillThread, KeyHandler keyH) {

        super(panel, speed, skillThread);

        this.panel = panel;
        this.keyH = keyH;
        this.attacking = false;

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
            punchUp = new BufferedImage[8];
            punchDown = new BufferedImage[8];
            punchLeft = new BufferedImage[8];
            punchRight = new BufferedImage[8];

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
            punchUp = new BufferedImage[8];
            punchDown = new BufferedImage[8];
            punchLeft = new BufferedImage[8];
            punchRight = new BufferedImage[8];


            int punch = 8;

            for (int i =0; i< punch;i++) {

                String fileMoveUp = "assets/knight/knightPunchUp-0" + (i+1) +".png";
                punchUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/knight/knightPunchDown-0" + (i+1) + ".png";
                punchDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/knight/knightPunchLeft-0" + (i+1) +".png";
                punchLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/knight/knightPunchRight-0" + (i+1) +".png";
                punchRight[i] = ImageIO.read(new File(fileMoveRight));
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
            updateAnimationPunch();
            moveAnimation();
            checkSkill();
            if(checkSkill == true && skill.alive == false && punchIndex == 6) {
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
        if (keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true ){

                direction = "up";
                posY -= speed;

            }
            if (keyH.downPressed == true ){

                direction = "down";
                posY += speed;
            }
            if (keyH.leftPressed == true ){

                direction = "left";
                posX -= speed;
            }
            if (keyH.rightPressed == true ){

                direction = "right";
                posX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum > 4) spriteNum = 1;
                else spriteNum ++;
                spriteCounter = 0;
            }
        }
    }
    public void moveFlashAnimation() {
        if (keyH.flashPressed == true) {
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

        checkPunching();


        if (attacking ) {

            switch (direction) {
                case "up":
                    currentFrameImg = punchUp[punchIndex ];

                    break;
                case "down":
                    currentFrameImg = punchDown[punchIndex];

                    break;
                case "left":
                    currentFrameImg = punchLeft[punchIndex];

                    break;
                case "right":
                    currentFrameImg = punchRight[punchIndex];

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
//        if(keyH.skillPressed == true && skill.alive == false) {
//            skill.set(posX, posY, direction,true, this);
//
//            // ADD TO A LIST
//            panel.skillList.add((Skill1ForKnight) skill);
//        }
    }
    public void updateAnimationPunch() {
        punchTick++;

        if (punchTick >= punchSpeed) {
            punchTick = 0;
            punchIndex ++;
            System.out.println(punchIndex);
            if (punchIndex > 7 ) {
                punchIndex = 0;
                attacking = false;
            }
        }
    }

    public void checkPunching() {
        if(keyH.spacePressed == true || keyH.skillPressed == true) {

            attacking = true;

        }

    }

    public void checkFlash() {
        if(keyH.flashPressed == true) {
            speed = 1000;
            flash = true;
        } else {
            speed = 2;         }
    }
    public void checkSkill(){
        if(keyH.skillPressed == true && skill.alive == false) {
//            skill.setSkill(posX, posY, direction, true, this);
//
//            // ADD skill to The list
//            panel.skillList.add(skill);
            checkSkill = true;
        }

    }
}
