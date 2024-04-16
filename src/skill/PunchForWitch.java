package skill;

import entity.Entity;
import entity.Skill;
import main.KeyHandler;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PunchForWitch extends Entity
{
    KeyHandler keyH;
    public double angle = 0;


    public int radius = 100; // R
   public int centerX ;
   public int centerY = panel.getHeight() / 2;
    public   int fireX ;
   public int fireY ;
    public PunchForWitch(Panel panel, int speed, int skillThread, KeyHandler keyH) {

        super(panel, speed, skillThread);
        this.keyH = keyH;
        this.panel = panel;
        this.speed = speed;
        this.attacking = false;
        getImage();
    }

    @Override
    public void update() {
        centerX = panel.getWidth() / 2;
        centerY = panel.getHeight() / 2;
        int speedFire = 100;
       angle += (System.currentTimeMillis() % 10000) / (10000.0 * speedFire) * 2 * Math.PI;
       fireX = (int) (centerX + radius * Math.cos(angle));
       fireY = (int) (centerY + radius * Math.sin(angle));
        spriteCounter++;
        if (spriteCounter > 3) {

            if (spriteNum > 7) spriteNum = 0;
            else spriteNum ++;
            spriteCounter = 0;
        }
    }


    @Override
    public void draw(Graphics2D g2) {

        BufferedImage currentFrameImg = null;

        currentFrameImg = fire[spriteNum];
        int fireWidth = panel.tileSize*2;
        int fireHeight = panel.tileSize*2;

        g2.drawImage(currentFrameImg, fireX - fireWidth/2 , fireY - fireHeight/2, panel.tileSize*2, panel.tileSize*2, null);
        System.out.println(fireX + fireY);


    }

    public void getImage() {
        fire = new BufferedImage[9];
        int move = 9;
        try {
            for (int i = 0; i < move; i++) {

                String fileFire = "assets/fire/fire-0" + (i + 1) + ".png";
                fire[i] = ImageIO.read(new File(fileFire));

            }
        }
        catch ( IOException e) {
            e.printStackTrace();
        }
    }
}
