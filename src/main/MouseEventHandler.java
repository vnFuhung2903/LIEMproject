package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseEventHandler implements MouseListener {

    private boolean leftClicked, rightClicked;

    public MouseEventHandler() {
        leftClicked = false;
        rightClicked = false;
    }

    public boolean isLeftClicked() {
        return leftClicked;
    }

    public boolean isRightClicked() {
        return rightClicked;
    }



    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 3) rightClicked = true;
        else leftClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        leftClicked = false;
        rightClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
