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
    BufferedImage uiInterface, uiWitch,uiE,uiQ, hp,mana,cooldownSkill;
    BufferedImage start, guide, backgroundStart, backgroundEnd, backgroundBoss, backgroundGuide, quit,returnGame, backgroundSetting, pointer, mute, nightMode;
    BufferedImage[] uiBoss, uiHpBoss, uiItem;
    public UI(Panel panel) {
        this.panel = panel;
        loadImage();

    }
    public void draw(Graphics2D g2) {

        if(panel.getCurrentState() == Panel.gameState.pauseState) {
            drawPauseScreen(g2);
            return;
        }
        if(panel.getCurrentState() == Panel.gameState.startState) {
            drawStartScreen(g2);
            return;
        }
        if(panel.getCurrentState()== Panel.gameState.ingameState) {
            drawPlayScreen(g2);
            return;
        }
        if(panel.getCurrentState()== Panel.gameState.guideState) {
            drawGuideScreen(g2);
        }
//        if (panel.hpPercent <= 0){
//            drawEndGame(g2);
//        }
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.drawImage(backgroundSetting,panel.screenWidth/2- panel.tileSize*3, panel.screenHeight/2- panel.tileSize*9/2, panel.tileSize*6, panel.tileSize*9, null);
        g2.drawImage(pointer,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize + panel.tileSize * panel.getCurrentPointer()*2, panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(returnGame,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(mute,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize + panel.tileSize, panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(quit,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize + panel.tileSize*3, panel.tileSize*4, panel.tileSize*2, null);
    }
    public void drawStartScreen(Graphics2D g2) {
        g2.drawImage(backgroundStart,0, 0, panel.screenWidth, panel.screenHeight, null);
//        g2.drawImage(backgroundStart,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize + panel.tileSize*panel.getCurrentPointer()*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(pointer,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize*2 + panel.tileSize*panel.getCurrentPointer()*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(start,panel.screenWidth/2 -panel.tileSize*2,panel.screenHeight/2-panel.tileSize - panel.tileSize*2 , panel.tileSize*4, panel.tileSize*2, null);
        g2.drawImage(guide,panel.screenWidth/2- panel.tileSize*2,panel.screenHeight/2-panel.tileSize  , panel.tileSize*4, panel.tileSize*2, null);

    }

    public void drawGuideScreen(Graphics2D g2) {
        g2.drawImage(backgroundGuide,0, 0, panel.screenWidth, panel.screenHeight, null);

    }
    public void drawPlayScreen(Graphics2D g2) {

        if(panel.encounterBoss() && panel.getBossHpPercent() > 0){
            g2.drawImage(backgroundBoss,0, 0, panel.screenWidth, panel.screenHeight, null);
        }
        else if(panel.night) {
            g2.drawImage(nightMode,0, 0, panel.screenWidth, panel.screenHeight, null);
        }
        g2.drawImage(uiWitch,0, 0, panel.tileSize*3, panel.tileSize*3, null);
        g2.drawImage(uiInterface,panel.tileSize/5, panel.tileSize*3, panel.tileSize*2, panel.tileSize*4, null);
        if(panel.getHpPercent()>0)
            g2.drawImage(hp,panel.tileSize/5 , panel.tileSize*15/4 + (panel.tileSize*139/48)*(100-panel.getHpPercent())/100, panel.tileSize*2 , (panel.tileSize*139/48)*panel.getHpPercent()/100, null);
        if(panel.getManaPercent()>0)
            g2.drawImage(mana,panel.tileSize/5 , panel.tileSize*15/4 +(panel.tileSize*139/48)*(100-panel.getManaPercent())/100, panel.tileSize*2, (panel.tileSize*139/48)*panel.getManaPercent()/100, null);

        g2.drawImage(uiQ,panel.tileSize , panel.screenHeight - panel.tileSize*3, panel.tileSize*2, panel.tileSize*2, null);
        g2.drawImage(uiE,panel.tileSize*3, panel.screenHeight - panel.tileSize*3, panel.tileSize*2, panel.tileSize*2, null);

        if(panel.countdownQ() < 100 )
            g2.drawImage(cooldownSkill,panel.tileSize , (panel.screenHeight - panel.tileSize*11/4) + panel.tileSize*3/2 *(100-  panel.countdownQ())/100 , panel.tileSize*2 , panel.tileSize*3/2 * panel.countdownQ()/100, null);
        if(panel.countdownE() < 100)
            g2.drawImage(cooldownSkill,panel.tileSize*3, (panel.screenHeight - panel.tileSize*11/4) + + panel.tileSize*3/2 *(100-  panel.countdownE())/100, panel.tileSize*2  , panel.tileSize*3/2* panel.countdownE()/100, null);

        if(panel.encounterBoss() && panel.getBossHpPercent() > 0) {
            g2.drawImage(uiBoss[panel.getBossId()], panel.tileSize * 37/2, 0, panel.tileSize * 7/2, panel.tileSize * 7/2, null);
            g2.drawImage(uiHpBoss[0], panel.tileSize * 17/2, panel.tileSize*3/2, panel.tileSize * 10, panel.tileSize, null);
            g2.drawImage(uiHpBoss[1], panel.tileSize * 35/4 , panel.tileSize*3/2, (panel.tileSize * 19/2) * panel.getBossHpPercent()/100, panel.tileSize, null);
        }

        int posX;
        int posY;
        int itemSize;

            posX = panel.screenWidth - panel.tileSize;
            posY = panel.screenHeight - panel.tileSize*2;
            itemSize = panel.tileSize*3/2;

        for(int i = 0;i < 3;i++){
            int numItem = 0;
            switch(i){
                case 0:
                    numItem = panel.numItemImmunity;
                    break;
                case 1:
                    numItem = panel.numItemHealMana;
                    break;
                case 2:
                    numItem = panel.numItemHealHp;
                    break;
            }
            g2.drawImage(uiItem[i] , posX  - itemSize*(i+1)  ,posY - itemSize/2, itemSize , itemSize,null);
            g2.drawImage(uiItem[4+numItem] , posX  - itemSize*(i+1)  ,posY - itemSize/2, itemSize , itemSize,null);
        }
//        if(panel.openItem){
//            g2.drawImage(uiItem[3] , panel.screenWidth/2 +  panel.tileSize*5 - itemSize*(panel.pointerItem + 1)  ,posY - itemSize/2, itemSize , itemSize,null);
//        }
    }

    public void drawEndGame(Graphics2D g2) {
        g2.drawImage(backgroundEnd,0, 0, panel.screenWidth, panel.screenHeight, null);

    }

    void loadImage() {
        try {
            uiBoss = new BufferedImage[2];
            uiHpBoss = new BufferedImage[2];

            String startImage = "assets/UI/button-01.png";
            start = ImageIO.read(new File(startImage));

            String guideImage = "assets/UI/button-02.png";
            guide = ImageIO.read(new File(guideImage));

            String settingImage = "assets/UI/button-03.png";
            returnGame= ImageIO.read(new File(settingImage));

            String quitImage = "assets/UI/button-04.png";
            quit= ImageIO.read(new File(quitImage));

            String muteImage = "assets/UI/button-05.png";
            mute= ImageIO.read(new File(muteImage));

            String pointerImage = "assets/UI/button-06.png";
            pointer = ImageIO.read(new File(pointerImage));

            String backgroundImage = "assets/UI/backgroundStart-01.png";
            backgroundStart =  ImageIO.read(new File(backgroundImage));

            String backgroundEndImage = "assets/UI/backgroundEnd-01.png";
            backgroundEnd =  ImageIO.read(new File(backgroundEndImage));

            String backgroundGuideImage = "assets/UI/backgroundGuide-01.png";
            backgroundGuide =  ImageIO.read(new File(backgroundGuideImage));

            String backgroundSettingImage = "assets/UI/backgroundSetting.png";
            backgroundSetting =  ImageIO.read(new File(backgroundSettingImage));

            String backgroundBossImage = "assets/UI/backgroundBoss-01.png";
            backgroundBoss =  ImageIO.read(new File(backgroundBossImage));

            String hpImage = "assets/UI/hp-01.png";
            hp =  ImageIO.read(new File(hpImage));

            String manaImage = "assets/UI/mana-01.png";
            mana =  ImageIO.read(new File(manaImage));

            String witchImage = "assets/UI/uiWitch-01.png";
            uiWitch =  ImageIO.read(new File(witchImage));

            String QImage = "assets/UI/uiSkillQ-01.png";
            uiQ =  ImageIO.read(new File(QImage));

            String EImage = "assets/UI/uiSkillE-01.png";
            uiE =  ImageIO.read(new File(EImage));

            String cooldownImage = "assets/UI/uiSkillCooldown-01.png";
            cooldownSkill =  ImageIO.read(new File(cooldownImage));

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

            uiItem = new BufferedImage[8];
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
            String Image6 = "assets/UI/uiItem-06.png";
            uiItem[5] = ImageIO.read(new File(Image6));
            String Image7 = "assets/UI/uiItem-07.png";
            uiItem[6] = ImageIO.read(new File(Image7));
            String Image8 = "assets/UI/uiItem-08.png";
            uiItem[7] = ImageIO.read(new File(Image8));



        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
