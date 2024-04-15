package main;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import entity.*;
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
    public entity.Character player = new entity.Character(this, 10, keyHandler, mouseEventHandler);
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
        monsters[0].update();
        monsters[1].update();
        monsters[2].update();
        monsters[3].update();

        player.update();
        for (Entity entity : skillList) {
            if (entity != null) {
                if (entity.isAlive()) {
                    entity.update();
                    System.out.println("loading");
                }
//                else {
//                     skillList.remove(i);
//                 }
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
        for(int i = 0; i <= 3; ++i) {
            boolean created = false;
            while (!created) {
                Random randomX = new Random();
                Random randomY = new Random();
                int x = randomX.nextInt(mapWidth) + 1;
                int y = randomY.nextInt(mapHeight) + 1;
                if(mapTile.getMapTileNum(x / tileSize, y / tileSize) == 1 && mapTile.getMapTileNum(x / tileSize + 1, y / tileSize) == 1 && mapTile.getMapTileNum(x / tileSize, y / tileSize + 1) == 1 && mapTile.getMapTileNum(x / tileSize + 1, y / tileSize + 1) == 1) {
                    monsters[i] = new Monster(this, 1, 0);
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