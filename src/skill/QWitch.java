package skill;

import entity.Skill;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QWitch extends Skill {

    public QWitch(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);
        this.posX = posX;
        this.posY = posY;
        this.panel = panel;
        alive = false;
        getImage();
    }

    public void getImage() {
        try {
            skillUp = new BufferedImage[8];
            skillDown = new BufferedImage[8];
            skillLeft = new BufferedImage[8];
            skillRight = new BufferedImage[8];
            for (int i = 1; i < 4; ++i) {
                String fileAttackUp = "assets/laze/witchLazeUp-0" + i + ".png";
                skillUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/laze/witchLazeDown-0" + i + ".png";
                skillDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/laze/witchLazeLeft-0" + i + ".png";
                skillLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/laze/witchLazeRight-0" + i + ".png";
                skillRight[i] = ImageIO.read(new File(fileAttackRight));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        skillThread--;
        if (skillThread <= 0) {
            if (spriteNum > 3) spriteNum = 1;
            else spriteNum++;
            alive = false;
            skillThread = 20;
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
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
                    currentFrameImg = skillUp[spriteNum];
                    g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 6, null);
                    break;
                case "down":
                    currentFrameImg = skillDown[spriteNum];
                    g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 2, panel.tileSize * 6, null);
                    break;
                case "left":
                    currentFrameImg = skillLeft[spriteNum];
                    g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 6, panel.tileSize * 2, null);

                    break;
                case "right":
                    currentFrameImg = skillRight[1];
                    g2.drawImage(currentFrameImg, screenX, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                    break;

            }
            System.out.println("pewwwwwww");
        }
    }
}