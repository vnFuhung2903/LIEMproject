package skill;

import entity.Skill;
import main.Panel;

import javax.imageio.ImageIO;
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
            skillUp = ImageIO.read(new File("assets/skill/skillsUp.png"));
            skillDown = ImageIO.read(new File("assets/skill/skillsDown.png"));
            skillLeft = ImageIO.read(new File("assets/skill/skillsLeft.png"));
            skillRight = ImageIO.read(new File("assets/skill/skillsRight.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
