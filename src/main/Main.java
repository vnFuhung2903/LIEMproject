package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame(); // create a window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the window
        window.setResizable(false); //can't resize window
        window.setTitle("LIEM");
        Panel panel = new Panel();
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        // not spesify the location of the window: it means the window be display at the center of the screen
        window.setVisible(true); //can see the window
        panel.startThread();
    }

}
