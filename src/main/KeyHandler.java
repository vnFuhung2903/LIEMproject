package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    Panel panel;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    boolean spacePressed, QPressed, EPressed, flashPressed;
    public KeyHandler(Panel panel) {
        this.panel = panel;
    }

    public boolean isMoving() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }

    public boolean isUsingSkillQ() {
        return QPressed;
    }
    public boolean isUsingSkillE() { return EPressed; }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_UP:
                if(--panel.pointerState < 0)  panel.pointerState = panel.maxState;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_DOWN:
               if(++panel.pointerState > panel.maxState)  panel.pointerState = 0;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            case KeyEvent.VK_J:
                QPressed = true;
                break;
            case KeyEvent.VK_K:
                EPressed = true;
                break;

            case KeyEvent.VK_I:
                if(panel.openItem) panel.openItem = false;
                else panel.openItem = true;
                break;
            case KeyEvent.VK_RIGHT:
                if(--panel.pointerItem<0) panel.pointerItem = 2;
                break;
            case KeyEvent.VK_LEFT:
                if(++panel.pointerItem>2) panel.pointerItem = 0;
                break;
            case KeyEvent.VK_P:
                if(panel.gameState==panel.playState) {
                    panel.gameState = panel.pauseState;
                }
                else if(panel.gameState==panel.pauseState) {
                    panel.gameState = panel.playState;
                }
                break;
            case KeyEvent.VK_ENTER:
                if(panel.gameState==panel.startState && panel.pointerState == panel.start) {
                    panel.gameState = panel.playState;
                    panel.maxState = 2;
                }
                else if(panel.gameState==panel.startState  && panel.pointerState == panel.guide) {
                    panel.gameState = panel.guideState;
                }
                else if(panel.gameState == panel.guideState ) {
                    panel.gameState = panel.playState;
                }
                else if(panel.pointerState == panel.guide ) {
                    panel.gameState = panel.guideState;
                }
                else if(panel.pointerState == panel.quit && panel.gameState == panel.pauseState) {
                    System.exit(0);
                    panel.maxState = 1;
                    panel.gameState = panel.startState;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
            case KeyEvent.VK_J:
                QPressed = false;
                break;
            case KeyEvent.VK_K:
                EPressed = false;
                break;
            case KeyEvent.VK_M:
                panel.setEffect(panel.getPlayer(), "dispel", 50, 2);
                break;
            case KeyEvent.VK_ENTER:
                 if(panel.openItem) {
                switch(panel.pointerItem){
                    case 2:
                        if(panel.numItemHealHp>0){panel.numItemHealHp--;
                            panel.setEffect(panel.getPlayer(), "healing", 5, 2);}
                        break;
                    case 1:
                        if(panel.numItemHealMana>0){panel.numItemHealMana--;
                            panel.setEffect(panel.getPlayer(), "healingMana", 5, 2);}
                        break;
                    case 0:
                        if(panel.numItemDispel>0)panel.numItemDispel--;
                        break;
                }
            }
        }
    }
}