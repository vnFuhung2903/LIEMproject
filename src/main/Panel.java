package main;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public AssetSetter assetSetter = new AssetSetter(this) ;
    TileManage mapTile = new TileManage(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public entity.Character player = new entity.Character(this, 20, 10, keyH);
    public Monster[] monster = new Monster[10];
    public EventHandler eHandler = new EventHandler(this);
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> skillList = new ArrayList<>();

    public Panel() {

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        // reduce flicking when drawing and updating objects on the screen
        // in somes cases, using double buffered my increase resource usage and latency (tang tai nguyen va do tre)
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        // it can receive keyboard and input events when it has input focus
        setUpGame();


    }
    public void setUpGame() {
        assetSetter.setMonster();

    }
    public  void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
/*
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            System.out.println("The game loop is running");
            // long currentTime = System.nanoTime();
            // long currentTime2 = System.currentTimeMillis();

            // 1 UPDATE: update information such as character position
            update();
            // 2 DRAW: draw the screen with the updated information
            repaint();
            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/100000;
                if (remainingTime < 0) remainingTime = 0;

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

 */

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
        monster[0].update();
        monster[1].update();
        monster[2].update();
        monster[3].update();

        player.update();
        for(int i = 0; i < skillList.size(); i++){
         if(skillList.get(i) != null){
            if(skillList.get(i).alive == true) {
                skillList.get(i).update();
                System.out.println("loading");

            }
            else {
                 skillList.remove(i);

             }
    }
}
    }




    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw map tiles
        mapTile.draw(g2);


        // ADD ENTITIES TO THE LIST
        for( int i = 0; i < monster.length ; i++){
            if (monster[i] != null) {
                entityList.add(monster[i]);
            }
        }
        for( int i = 0; i < skillList.size() ; i++){
            if (skillList.get(i) != null) {
                entityList.add(skillList.get(i));
            }
        }
        entityList.add(player);


        // SORT
        Collections.sort(entityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity entity1, Entity entity2) {
                int  result = Integer.compare(entity1.posY, entity2.posY);
                return 0;
            }
            // so sánh toạ độ của các entity

        });

        // DRAW ENTITIES
        for(int i = 0; i< entityList.size(); i++){
            entityList.get(i).draw(g2);

        }
        for(int i = 0; i< entityList.size(); i++){
            entityList.remove(i);




        }
        // EMPTY ENTITY LIST

//        // Draw monster
//        monster[0].draw(g2);
//
//        // Draw player character
//        player.draw(g2);

        g2.dispose();
    }
}