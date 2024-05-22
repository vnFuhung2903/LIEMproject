package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UI {
    Panel panel;
    BufferedImage start,guide,backgroundStart,backgroundGuide,quit,setting,backgroundSetting,pointer;
    public UI(Panel panel) {
        this.panel = panel;
        loadImage();

    }
    public void draw(Graphics2D g2) {

        if(panel.gameState==panel.pauseState) {
            drawPauseScreen(g2);
        }
        else if(panel.gameState==panel.startState) {
            drawStartScreen(g2);
        }
        else if(panel.gameState==panel.guideState) {
            drawGuideScreen(g2);
        }
    }
    public void drawPauseScreen(Graphics2D g2) {
        g2.drawImage(backgroundSetting,panel.screenWidth/2- panel.tileSize*6, panel.screenHeight/2- panel.tileSize*9/2, panel.tileSize*12, panel.tileSize*9, null);
        g2.drawImage(pointer,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize + panel.tileSize*panel.pointerState*2, panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(quit,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(guide,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize +panel.tileSize, panel.tileSize*4, panel.tileSize*2, null);
    }
    public void drawStartScreen(Graphics2D g2) {
        g2.drawImage(backgroundStart,0, 0, panel.screenWidth, panel.screenHeight, null);
//        g2.drawImage(backgroundStart,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize + panel.tileSize*panel.pointerState*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(pointer,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize*2 + panel.tileSize*panel.pointerState*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(start,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(guide,panel.screenWidth/2- panel.tileSize*2,panel.screenHeight/2-panel.tileSize  , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(setting,panel.screenWidth/2- panel.tileSize*2,panel.screenHeight/2-panel.tileSize  + panel.tileSize*2, panel.tileSize*4, panel.tileSize*2, null);
    }
    public void drawGuideScreen(Graphics2D g2) {
        g2.drawImage(backgroundGuide,0, 0, panel.screenWidth, panel.screenHeight, null);

    }
    public void loadImage(){
        try {
            String startImage = "assets/UI/button-01.png";
            start = ImageIO.read(new File(startImage));

            String guideImage = "assets/UI/button-02.png";
            guide = ImageIO.read(new File(guideImage));

            String settingImage = "assets/UI/button-03.png";
            setting= ImageIO.read(new File(settingImage));

            String pointerImage = "assets/UI/button-04.png";
            pointer = ImageIO.read(new File(pointerImage));

            String quitImage = "assets/UI/button-05.png";
            quit= ImageIO.read(new File(quitImage));



            String backgroundImage = "assets/UI/backgroundStart-01.png";
            backgroundStart =  ImageIO.read(new File(backgroundImage));

            String backgroundGuideImage = "assets/UI/backgroundGuide-01.png";
            backgroundGuide =  ImageIO.read(new File(backgroundGuideImage));

            String backgroundSettingImage = "assets/UI/backgroundSetting-01.png";
            backgroundSetting =  ImageIO.read(new File(backgroundSettingImage));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
