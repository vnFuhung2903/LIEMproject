package entity.characters;

import entity.Character;
import main.KeyHandler;
import main.MouseEventHandler;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Witch extends Character {

    public Witch(Panel panel, int skillThread, KeyHandler keyHandler, MouseEventHandler mouseEventHandler) {
        super(panel, skillThread, keyHandler, mouseEventHandler);
        this.attackInterval = 15;
        this.collisionArea = new Rectangle(panel.tileSize , panel.tileSize , 0, panel.tileSize / 2);
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

            for (int i =0; i < 6; ++i) {
                String fileMoveUp = "assets/witch/witchMoveUp-0" + (i + 1) +".png";
                moveUp[i] = ImageIO.read(new File(fileMoveUp));
                String fileMoveDown = "assets/witch/witchMoveDown-0" + (i + 1) + ".png";
                moveDown[i] = ImageIO.read(new File(fileMoveDown));
                String fileMoveLeft = "assets/witch/witchMoveLeft-0" + (i + 1) +".png";
                moveLeft[i] = ImageIO.read(new File(fileMoveLeft));
                String fileMoveRight = "assets/witch/witchMoveRight-0" + (i + 1) +".png";
                moveRight[i] = ImageIO.read(new File(fileMoveRight));
            }

            for(int i = 0; i < 8; ++i) {
                String fileAttackUp = "assets/witch/witchAttack/witchQUp-0" + (i + 1) +".png";
                attackUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/witch/witchAttack/witchQDown-0" + (i + 1) + ".png";
                attackDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/witch/witchAttack/witchQLeft-0" + (i + 1) +".png";
                attackLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/witch/witchAttack/witchQRight-0" + (i + 1) +".png";
                attackRight[i] = ImageIO.read(new File(fileAttackRight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}