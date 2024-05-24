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
                panel.modifyPointer(-1, panel.startOption);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = true;
                panel.modifyPointer(1, panel.startOption);
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
                    panel.sound.igMusic.loop(Clip.LOOP_CONTINUOUSLY);
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
                    panel.sound.igMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    panel.resetCurrentPointer();
                    break;
                }
                if(panel.getCurrentState() == Panel.gameState.pauseState &&  Objects.equals(panel.getCurrentOption(panel.pauseOption), "quit")) {
                    panel.setCurrentState(Panel.gameState.startState);
                    if(panel.encounterBoss()) panel.sound.bossMusic.close();
                    else panel.sound.igMusic.close();
                    panel.sound.startMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    panel.resetCurrentPointer();
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