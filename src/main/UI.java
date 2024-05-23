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
    BufferedImage uiInterface,uiItem[],uiWitch,uiE,uiQ,uiCoolDownSkill,hp,mana;
    BufferedImage start,guide,backgroundStart,backgroundGuide,quit,setting,backgroundSetting,pointer,mute,nightMode;
    BufferedImage uiBoss[], uiHpBoss[];
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
        else if(panel.gameState==panel.playState) {
            drawPlayScreen(g2);
        }
        else if(panel.gameState==panel.guideState) {
            drawGuideScreen(g2);
        }
    }
    public void drawPauseScreen(Graphics2D g2) {
        g2.drawImage(backgroundSetting,panel.screenWidth/2- panel.tileSize*3, panel.screenHeight/2- panel.tileSize*9/2, panel.tileSize*6, panel.tileSize*9, null);
        g2.drawImage(pointer,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize + panel.tileSize*panel.pointerState*2, panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(quit,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(guide,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize + panel.tileSize, panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(mute,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize + panel.tileSize*3, panel.tileSize*4, panel.tileSize*2, null);
    }
    public void drawStartScreen(Graphics2D g2) {
        g2.drawImage(backgroundStart,0, 0, panel.screenWidth, panel.screenHeight, null);
//        g2.drawImage(backgroundStart,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize + panel.tileSize*panel.pointerState*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(pointer,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize*2 + panel.tileSize*panel.pointerState*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(start,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(guide,panel.screenWidth/2- panel.tileSize*2,panel.screenHeight/2-panel.tileSize  , panel.tileSize*4, panel.tileSize*2, null);
//        g2.drawImage(setting,panel.screenWidth/2- panel.tileSize*2,panel.screenHeight/2-panel.tileSize  + panel.tileSize*2, panel.tileSize*4, panel.tileSize*2, null);
    }
    public void drawGuideScreen(Graphics2D g2) {
        g2.drawImage(backgroundGuide,0, 0, panel.screenWidth, panel.screenHeight, null);

    }
    public void drawPlayScreen(Graphics2D g2) {
       if(panel.night) {
           g2.drawImage(nightMode,0, 0, panel.screenWidth, panel.screenHeight, null);
       }
        g2.drawImage(uiWitch,0, 0, panel.tileSize*7/2, panel.tileSize*7/2, null);
        g2.drawImage(uiInterface,panel.tileSize/6, panel.tileSize*7/2, panel.tileSize*3, panel.tileSize*6, null);
        g2.drawImage(hp, panel.tileSize*19/30 + 15/2 , panel.tileSize*9/2 + panel.tileSize/4 + 1, panel.tileSize/2 - 3, panel.tileSize*26/6, null);
        g2.drawImage(mana,panel.tileSize*145/72 + 19/2 , panel.tileSize*9/2 + panel.tileSize/4 + 1, panel.tileSize/2 - 3, panel.tileSize*26/6, null);
        g2.drawImage(uiQ,panel.tileSize , panel.screenHeight - panel.tileSize*3, panel.tileSize*3/2, panel.tileSize*3/2, null);
        g2.drawImage(uiE,panel.tileSize*3, panel.screenHeight - panel.tileSize*3, panel.tileSize*3/2, panel.tileSize*3/2, null);
        if(1==1) {
            g2.drawImage(uiBoss[0], panel.tileSize * 15, 0, panel.tileSize * 3, panel.tileSize * 3, null);
            g2.drawImage(uiHpBoss[0], panel.tileSize * 5, panel.tileSize*3/2, panel.tileSize * 10, panel.tileSize/2, null);
            g2.drawImage(uiHpBoss[1], panel.tileSize * 5, panel.tileSize*3/2, panel.tileSize * 10*50/200, panel.tileSize/2, null);
        }

        int posX;
        int posY;
        int itemSize;
        if(panel.openItem){
            posX = panel.screenWidth/2 +  panel.tileSize*13/2;
            posY = panel.screenHeight/2;
            itemSize = panel.tileSize*3;
            g2.drawImage(uiItem[4] , posX  - itemSize*(panel.pointerItem + 1)  ,posY - itemSize/2, itemSize , itemSize,null);
        }
        else{
            posX = panel.screenWidth - panel.tileSize;
            posY = panel.screenHeight - panel.tileSize*2;
            itemSize = panel.tileSize*3/2;
        }
        for(int i = 0;i <4;i++){
            g2.drawImage(uiItem[i] , posX  - itemSize*(i+1)  ,posY - itemSize/2, itemSize , itemSize,null);
        }
    }
    public void loadImage(){
        try {
            uiBoss = new BufferedImage[2];
            uiHpBoss = new BufferedImage[2];

            String startImage = "assets/UI/button-01.png";
            start = ImageIO.read(new File(startImage));

            String guideImage = "assets/UI/button-02.png";
            guide = ImageIO.read(new File(guideImage));

            String settingImage = "assets/UI/button-03.png";
            setting= ImageIO.read(new File(settingImage));

            String quitImage = "assets/UI/button-04.png";
            quit= ImageIO.read(new File(quitImage));

            String muteImage = "assets/UI/button-05.png";
            mute= ImageIO.read(new File(muteImage));

            String pointerImage = "assets/UI/button-06.png";
            pointer = ImageIO.read(new File(pointerImage));

            String backgroundImage = "assets/UI/backgroundStart-01.png";
            backgroundStart =  ImageIO.read(new File(backgroundImage));

            String backgroundGuideImage = "assets/UI/backgroundGuide-01.png";
            backgroundGuide =  ImageIO.read(new File(backgroundGuideImage));

            String backgroundSettingImage = "assets/UI/backgroundSetting.png";
            backgroundSetting =  ImageIO.read(new File(backgroundSettingImage));

            String hpImage = "assets/UI/hp-01.png";
            hp =  ImageIO.read(new File(hpImage));

            String manaImage = "assets/UI/mana-01.png";
            mana =  ImageIO.read(new File(manaImage));

            String witchImage = "assets/UI/uiWitch-01.png";
            uiWitch =  ImageIO.read(new File(witchImage));

            String QImage = "assets/UI/uiSkillQ.png";
            uiQ =  ImageIO.read(new File(QImage));

            String EImage = "assets/UI/uiSkillE.png";
            uiE =  ImageIO.read(new File(EImage));

            String nightImage = "assets/nightmode/night-01.png";
            nightMode = ImageIO.read(new File(nightImage));

            String interfaceImage = "assets/UI/uiInterface-01.png";
            uiInterface = ImageIO.read(new File(interfaceImage));

            String bossImage1 = "assets/UI/uiBoss-01.png";
            uiBoss[0] = ImageIO.read(new File(bossImage1));

            String bossImage2 = "assets/UI/uiBoss-02.png";
            uiBoss[1] = ImageIO.read(new File(bossImage2));

            String hpBossImage1 = "assets/UI/hpBoss-01.png";
            uiHpBoss[0] = ImageIO.read(new File(hpBossImage1));

            String hpBossImage2 = "assets/UI/hpBoss-02.png";
            uiHpBoss[1] = ImageIO.read(new File(hpBossImage2));

            uiItem = new BufferedImage[5];
            String Image1 = "assets/UI/uiItem-01.png";
            uiItem[0] = ImageIO.read(new File(Image1));
            String Image2 = "assets/UI/uiItem-02.png";
            uiItem[1] = ImageIO.read(new File(Image2));
            String Image3 = "assets/UI/uiItem-03.png";
            uiItem[2] = ImageIO.read(new File(Image3));
            String Image4 = "assets/UI/uiItem-04.png";
            uiItem[3] = ImageIO.read(new File(Image4));
            String Image5 = "assets/UI/uiItem-05.png";
            uiItem[4] = ImageIO.read(new File(Image5));


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
