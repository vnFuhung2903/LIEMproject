package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, QPressed, EPressed, flashPressed;

    public boolean isMoving() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }

    public boolean isUsingSkill() {
        return QPressed || EPressed;
    }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = true;
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
            case KeyEvent.VK_K:
                flashPressed = true;
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
            case KeyEvent.VK_K:
                flashPressed = false;
                break;
        }
    }
}