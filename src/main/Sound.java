package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {
    public Clip ingameMusic, startMusic, bossPhaseMusic;
    FloatControl ingameMusicVolControl, bossPhaseVolControl;
    public Sound() {
        getMusic();
    }

    void getMusic() {
        try {
            File file = new File("sound/start.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            startMusic = AudioSystem.getClip();
            startMusic.open(audioInputStream);

            file = new File("sound/ingame.wav");
            audioInputStream = AudioSystem.getAudioInputStream(file);
            ingameMusic = AudioSystem.getClip();
            ingameMusic.open(audioInputStream);

            file = new File("sound/boss.wav");
            audioInputStream = AudioSystem.getAudioInputStream(file);
            bossPhaseMusic = AudioSystem.getClip();
            bossPhaseMusic.open(audioInputStream);
            
            ingameMusicVolControl = (FloatControl) ingameMusic.getControl(FloatControl.Type.MASTER_GAIN);
            bossPhaseVolControl = (FloatControl) bossPhaseMusic.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
