package main;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import entity.*;
import entity.monsters.*;
import map.*;

public class Panel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16*16 tile
    final int scale = 3;
    final public int tileSize = originalTileSize * scale ; // 48*48 tile
    final int characterScale = 2;
    final public int characterSize = tileSize * characterScale;
    final int maxScreenCol = 20;
    final int maxScreenRow = 12;
    final public int screenWidth = tileSize * maxScreenCol; // 960 pixels
    final public int screenHeight = tileSize * maxScreenRow; // 576 pixels


    // MAP SETTINGS
    final public int maxMapCol = 2000;
    final public int maxMapRow = 2000;
    final public int mapWidth = tileSize * maxScreenCol;
    final public int mapHeight = tileSize * maxScreenRow;

    // FPS
    double FPS = 60;

    // Systems
    TileManage mapTile = new TileManage(this);
    KeyHandler keyHandler = new KeyHandler();
    MouseEventHandler mouseEventHandler = new MouseEventHandler();
    Thread gameThread;
    public CollisionHandler collisionHandler = new CollisionHandler(this);

    // Entities
    public entity.Character player = new entity.characters.Witch(this, 10, keyHandler, mouseEventHandler);
    public Monster[] monsters = new Monster[10];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> skillList = new ArrayList<>();

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        // reduce flicking when drawing and updating objects on the screen
        // in some cases, using double buffered my increase resource usage and latency (tang tai nguyen va do tre)
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseEventHandler);
        this.setFocusable(true);
        // it can receive keyboard and input events when it has input focus
        setUpGame();


    }
    public void setUpGame() {
        setMonsters();

    }
    public  void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){

                update();
                repaint();

                delta--;
            }
        }
    }
    public  void update() {

        for(Monster monster : monsters) {
            monster.update();
        }

        player.update();
        for (Entity entity : skillList) {
            if (entity != null) {
                if (entity.isAlive()) {
                    entity.update();
                    System.out.println("loading");
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Draw map tiles
        mapTile.draw(g2);

        // ADD ENTITIES TO THE LIST
        for (Monster value : monsters) {
            if (value != null) {
                entityList.add(value);
            }
        }
        for (Entity entity : skillList) {
            if (entity != null) {
                entityList.add(entity);
            }
        }
        entityList.add(player);

        // SORT entities in posY
        entityList.sort(Comparator.comparingInt(Entity::getPosY));

        // DRAW ENTITIES
        for (Entity entity : entityList) {
            entity.draw(g2);
        }

        // Make entityList empty after drawing
        entityList.clear();
        g2.dispose();
    }

    public Monster[] getMonsters() { return monsters; }
    public void setMonsters() {

        for(int i = 0; i <= 9; ++i) {
            boolean created = false;
            while (!created) {

                Random randomColor = new Random();
                int colorIndex = randomColor.nextInt(5);
                String color = "Blue";
                switch (colorIndex) {
                    case 1:
                        color = "Blue";
                        break;
                    case 2:
                        color = "Red";
                        break;
                    case 3:
                        color = "Yellow";
                        break;
                    case 4:
                        color = "Green";
                        break;
                }

                Random randomX = new Random();
                Random randomY = new Random();
                int x = randomX.nextInt(mapWidth) + 1;
                int y = randomY.nextInt(mapHeight) + 1;

                if(mapTile.getMapTileNum(x / tileSize, y / tileSize) == 1) {
                    if(i < 5) monsters[i] = new Slime(this, 5, 0, color);
                    else if(i < 8) monsters[i] = new Spider(this, 5, 0);
                    else monsters[i] = new Slave(this, 5, 10);
                    monsters[i].setPosX(x);
                    monsters[i].setPosY(y);
                    System.out.print(x);
                    System.out.println(y);
                    created = true;
                }
            }
        }
    }
}