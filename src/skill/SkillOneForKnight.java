package skill;

import entity.Skill;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkillOneForKnight extends Skill {

    public SkillOneForKnight(Panel panel, int speed, int skillThread) {
        super(panel, speed, skillThread);

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
            skillUp[0] = ImageIO.read(new File("assets/skill/skillsUp.png"));
            skillDown[0] = ImageIO.read(new File("assets/skill/skillsDown.png"));
            skillLeft[0] = ImageIO.read(new File("assets/skill/skillsLeft.png"));
            skillRight[0]= ImageIO.read(new File("assets/skill/skillsRight.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}