package main;

import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    //Screen setting:
    final int tiledBlock = 16;
    final int tiledScale = 3;
    final int tiledSize = tiledBlock * tiledScale;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Thread thread;

    public Panel() {
        this.setPreferredSize(screenSize);
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
    }

    @Override
    public void run() {
        while(thread != null) {
            System.out.println("Running");
        }
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }
}