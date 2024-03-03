package main;

import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        //Create the main frame (named window)
        JFrame window = new JFrame("LIEM");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().setBackground(Color.black);
        window.setUndecorated(true);
        window.setResizable(false);

        //Add component
        Panel panel = new Panel();
        window.add(panel);

        window.pack();

        window.setVisible(true);

        panel.startThread();
    }
}