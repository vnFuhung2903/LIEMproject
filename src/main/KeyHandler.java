package main;

import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {
    Panel panel;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    boolean spacePressed, QPressed, EPressed, Item1Pressed, Item2Pressed, Item3Pressed;
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
    public int getItemPressed() {
        if(Item1Pressed) return 0;
        if(Item2Pressed) return 1;
        if(Item3Pressed) return 2;
        return -1;
    }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = true;
                if(panel.getCurrentState() == Panel.gameState.startState) panel.modifyPointer(-1, panel.startOption);
                if(panel.getCurrentState() == Panel.gameState.pauseState) panel.modifyPointer(-1, panel.pauseOption);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = true;
                if(panel.getCurrentState() == Panel.gameState.startState) panel.modifyPointer(1, panel.startOption);
                if(panel.getCurrentState() == Panel.gameState.pauseState) panel.modifyPointer(1, panel.pauseOption);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            case KeyEvent.VK_Q:
                QPressed = true;
                break;
            case KeyEvent.VK_E:
                EPressed = true;
                break;
            case KeyEvent.VK_1:
                Item1Pressed = true;
                break;
            case KeyEvent.VK_2:
                Item2Pressed = true;
                break;
            case KeyEvent.VK_3:
                Item3Pressed = true;
                break;
            case KeyEvent.VK_P:
                if(panel.getCurrentState() == Panel.gameState.ingameState) {
                    panel.setCurrentState(Panel.gameState.pauseState);
                }
                else if(panel.getCurrentState() == Panel.gameState.pauseState) {
                    panel.setCurrentState(Panel.gameState.ingameState);
                }
                panel.resetCurrentPointer();
                break;
            case KeyEvent.VK_ENTER:
                if(panel.getCurrentState() == Panel.gameState.startState && Objects.equals(panel.getCurrentOption(panel.startOption), "start")) {
                    panel.setCurrentState(Panel.gameState.ingameState);
                    panel.sound.startMusic.close();
                    panel.sound.ingameMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    panel.resetCurrentPointer();
                    break;
                }
                if(panel.getCurrentState() == Panel.gameState.startState  && Objects.equals(panel.getCurrentOption(panel.startOption), "guide")) {
                    panel.setCurrentState(Panel.gameState.guideState);
                    break;
                }
                if(panel.getCurrentState() == Panel.gameState.guideState) {
                    panel.setCurrentState(Panel.gameState.ingameState);
                    panel.sound.startMusic.close();
                    panel.sound.ingameMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    panel.resetCurrentPointer();
                    break;
                }
                if(panel.getCurrentState() == Panel.gameState.pauseState) {
                    if(Objects.equals(panel.getCurrentOption(panel.pauseOption), "quit")) {
                        System.exit(0);
                        break;
                    }
                    if(Objects.equals(panel.getCurrentOption(panel.pauseOption), "mute")) {
                        if(panel.isMuted()) {
                            panel.setMuted();
                            if (panel.encounterBoss()) panel.sound.bossPhaseVolControl.setValue(0.0f);
                            else panel.sound.ingameMusicVolControl.setValue(0.0f);
                        }
                        else {
                            panel.setMuted();
                            if (panel.encounterBoss()) panel.sound.bossPhaseVolControl.setValue(-80.0f);
                            else panel.sound.ingameMusicVolControl.setValue(-80.0f);
                        }
                        break;
                    }
                    panel.setCurrentState(Panel.gameState.ingameState);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
            case KeyEvent.VK_Q:
                QPressed = false;
                break;
            case KeyEvent.VK_E:
                EPressed = false;
                break;
            case KeyEvent.VK_1:
                Item1Pressed = false;
                break;
            case KeyEvent.VK_2:
                Item2Pressed = false;
                break;
            case KeyEvent.VK_3:
                Item3Pressed = false;
                break;
        }
    }
}