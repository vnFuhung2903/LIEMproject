package entity.skills;

import entity.*;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WitchQ extends Skill {

    public WitchQ(Panel panel, int speed, int skillThread, Entity user) {
        super(panel, speed, skillThread, user);
        getSkillImage();
    }

    public void getSkillImage() {
        try {
            skillUp = new BufferedImage[3];
            skillDown = new BufferedImage[3];
            skillLeft = new BufferedImage[3];
            skillRight = new BufferedImage[3];

            for (int i = 0; i < 3; ++i) {
                String fileAttackUp = "assets/laze/witchLazeUp-0" + (i + 1) + ".png";
                skillUp[i] = ImageIO.read(new File(fileAttackUp));
                String fileAttackDown = "assets/laze/witchLazeDown-0" + (i + 1) + ".png";
                skillDown[i] = ImageIO.read(new File(fileAttackDown));
                String fileAttackLeft = "assets/laze/witchLazeLeft-0" + (i + 1) + ".png";
                skillLeft[i] = ImageIO.read(new File(fileAttackLeft));
                String fileAttackRight = "assets/laze/witchLazeRight-0" + (i + 1) + ".png";
                skillRight[i] = ImageIO.read(new File(fileAttackRight));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (--skillThread <= 0) {
            if (++spriteIndex > 2)
            {
                spriteIndex = 0;
                casted = false;
            }
            skillThread = 10;
        }
    }

    // Not set pos for Q because laze will change direction with its user
    public void setSkill() {
        casted = true;
        spriteIndex = 0;
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = panel.getPlayer().screenX;
        int screenY = panel.getPlayer().screenY;

        if (panel.tileSize >  - panel.getPlayer().screenX &&
                - panel.tileSize < + panel.getPlayer().screenX &&
                panel.tileSize > - panel.getPlayer().screenY &&
                - panel.tileSize < panel.getPlayer().screenY
        ) {
            System.out.println(spriteIndex);
            switch (panel.getPlayer().getDirection()) {
                case "up":
                    g2.drawImage(skillUp[spriteIndex], screenX, screenY - panel.tileSize * 19 / 4, panel.tileSize * 2, panel.tileSize * 6, null);
                    break;
                case "down":
                    g2.drawImage(skillDown[spriteIndex], screenX, screenY, panel.tileSize * 2, panel.tileSize * 6, null);
                    break;
                case "left":
                    g2.drawImage(skillLeft[spriteIndex], screenX - panel.tileSize * 4, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                    break;
                case "right":
                    g2.drawImage(skillRight[spriteIndex], screenX, screenY, panel.tileSize * 6, panel.tileSize * 2, null);
                    break;
            }
        }
    }
}