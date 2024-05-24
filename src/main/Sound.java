package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    public Clip igMusic, startMusic, bossMusic;
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
            igMusic = AudioSystem.getClip();
            igMusic.open(audioInputStream);

            file = new File("sound/boss.wav");
            audioInputStream = AudioSystem.getAudioInputStream(file);
            bossMusic = AudioSystem.getClip();
            bossMusic.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
