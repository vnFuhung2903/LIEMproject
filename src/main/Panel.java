package main;

import javax.swing.JPanel;
import java.awt.*;
import java.util.*;

import entity.*;
import entity.Character;
import entity.monsters.*;
import map.*;

public class Panel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16*16 tile
    final int scale = 4;
    final public int tileSize = originalTileSize * scale ; // 48*48 tile
    final public int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    final public int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;


    // MAP SETTINGS


    public int timeNight = 1000;
    final public int maxMapCol = 250;
    final public int maxMapRow = 250;
    final public int mapWidth = tileSize * maxMapRow;
    final public int mapHeight = tileSize * maxMapCol;

    // FPS
    double FPS = 60;

    // GAME STATE
    public int gameState;

    public int startState=0;
    public final int  playState = 1;
    public final int pauseState = 2;
    public final int guideState = 3;
    public final int start = 0;
    public final int guide = 1;
    public final int quit = 0;
    public final int mute = 2;
    public int maxState = 1;
    public int pointerState = 0;

    // UI
    public boolean night = false;
    public boolean openItem = false;
    public int pointerItem = 0;
    // Systems
    TileManage mapTile = new TileManage(this);
    Nightmode nightmode = new Nightmode(this);
    SandTrap sandTrap;
    SpiderCave spiderCave;
    KeyHandler keyHandler = new KeyHandler(this);
    MouseEventHandler mouseEventHandler = new MouseEventHandler();
    Thread gameThread;
    UI ui = new UI(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);

    // Entities
    entity.Character player = new entity.characters.Witch(this, 10, keyHandler, mouseEventHandler);
    ArrayList<Monster> monsters = new ArrayList<>();
    ArrayList<Skill> skillList = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Effect> effects = new ArrayList<>();
    MonsterAsset monsterAsset = new MonsterAsset(this);

    public Panel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(screenSize.width,screenSize.height));
        Color bgColor = new Color(243, 174, 92);
        this.setBackground(bgColor);

        // in some cases, using double buffered my increase resource usage and latency (tang tai nguyen va do tre)
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseEventHandler);
        this.setFocusable(true); // receive keyboard and input events
        setUpGame();
    }

    public void setUpGame() {
        gameState = startState;
        setMap();
        setMonsters();
    }

    public  void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

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

    public void update() {
        checkNight();
        if(gameState == pauseState || gameState == startState || gameState == guideState) {
            return;
        }
        if(sandTrap != null) sandTrap.update();

        monsters.removeIf(monster -> monster.getHp() <= 0);
        for(Monster monster : monsters) {
            monster.update();
        }

        items.removeIf(item -> !item.isCollectable());
        for(Item item : items) {
            if(item != null && item.isCollectable()) {
                item.update();
            }
        }

        effects.removeIf(effect -> !effect.isActive());
        for(Effect effect : effects) {
            if(effect != null) {
                effect.update();
            }
        }

        skillList.removeIf(skill -> !skill.isCasted());
        for(Skill skill : skillList) {
            if(skill != null) {
                skill.update();
            }
        }

        player.update();
//        for (Skill skill : skillList) {
//            if (skill != null) {
//                if (!skill.isCasted()) {
//                    skill.update();
//                }
//            }
//        }
    }
    void checkNight(){
       if(--timeNight < 0 ){
           if(night) night = false;
           else night = true;
           timeNight = 1000;
       }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        if(gameState == startState || gameState == guideState) {
            ui.draw(g2);
            g2.dispose();
            return;
        }


        // Draw map
        sandTrap.draw(g2);
        mapTile.draw(g2);
        spiderCave.draw(g2);

        // Sort entities in posY
        ArrayList<Entity> entities = new ArrayList<>(monsters);
        entities.add(player);
        entities.sort(Comparator.comparingInt(Entity::getPosY));

        // Draw others
        for (Entity entity : entities) {
            entity.draw(g2);
        }

        for(Item item : items) {
            item.draw(g2);
        }

        for(Effect effect : effects) {
            effect.draw(g2);
        }

        for(Skill skill : skillList) {
            skill.draw(g2);
        }

        if(gameState == pauseState || gameState == playState) {
            ui.draw(g2);
        }
//        nightmode.draw(g2);


        g2.dispose();
    }

    void setMap() {

        // Create sand trap
        boolean created = false;
        while (!created) {
            Random randomX = new Random();
            Random randomY = new Random();
            int x = randomX.nextInt(mapWidth);
            int y = randomY.nextInt(mapHeight);

            if (collisionHandler.checkSpawn(x, y, 4)) {
                sandTrap = new SandTrap(this, x, y);
                created = true;
            }
        }

        // Create spider cave
        created = false;
        while (!created) {
//            Random randomX = new Random();
//            Random randomY = new Random();
//            int x = randomX.nextInt(mapWidth);
//            int y = randomY.nextInt(mapHeight);

            int x = mapWidth / 2;
            int y = mapHeight / 2;

//            int x = player.getPosX();
//            int y = player.getPosY();

            if (collisionHandler.checkSpawn(x, y, 1)) {
                spiderCave = new SpiderCave(this, x, y);
                created = true;

//                for(int i = 0; i < 4; i++) {
//                    Spider monster =  new Spider(this, 5, 10, spiderCave);
//                    monster.setPosX(x + i + 1);
//                    monster.setPosY(y + i + 1);
//                    monsters.add(monster);
//                }
            }
        }
    }
    public void setMonstersGhost (int x,int y) {
        if(collisionHandler.checkSpawn(x, y, 2)) {
            Slave monster = new Slave(this, 2, 10);
            monster.setPosX(x);
            monster.setPosY(y);
            monsters.add(monster);
            System.out.print(x);
            System.out.println(y);
        }
    }
    void setMonsters() {

        for(int i = 0; i < 1; ++i) {
            boolean created = false;

            // Create up to: 50 slimes OR 50 spiders OR 20 slaves OR 50 goblins OR 5 hobs

            while (!created) {
//                Random randomX = new Random();
//                Random randomY = new Random();
//                int x = randomX.nextInt(mapWidth);
//                int y = randomY.nextInt(mapHeight);

                int x = mapWidth / 2;
                int y = mapHeight / 2;

                if(collisionHandler.checkSpawn(x, y, 1)) {
                    Skeleton monster = new Skeleton (this, 5, 10);
                    monster.setPosX(x);
                    monster.setPosY(y);
                    monsters.add(monster);
                    System.out.print(x);
                    System.out.println(y);
                    created = true;
                }
            }
        }

//        for(int i = 0; i < 100; ++i) {
//            boolean created = false;
//
//            while (!created) {
//
//                int x = mapWidth / 2;
//                int y = mapHeight / 2;
//
//                if(collisionHandler.checkSpawn(x, y, 3)) {
//                    Slave monster =  new Slave(this, 5, 10);
//                    monster.setPosX(x);
//                    monster.setPosY(y);
//                    monsters.add(monster);
//                    System.out.print(x);
//                    System.out.println(y);
//                    created = true;
//                }
//            }
//        }
    }

    public void createItem(int id, int posX, int posY) {
        Random random = new Random();
        int idx = random.nextInt(3);
        items.add(new Item(this, idx, posX, posY));
    }

    public void setEffect(Entity entity, String name, int time, int entitySize) {
        for(Effect effect : effects) {
            if(effect.getEntity() == entity && Objects.equals(effect.getName(), name)) {
//                effect.extend(time);
                return;
            }
        }
        effects.add(new Effect(this, entity, name, time, entitySize));
    }

    public void setSkill(Skill skill) {
        skillList.add(skill);
    }

    public ArrayList<Monster> getMonsters() { return monsters; }

    public Character getPlayer() { return player; }

    public MonsterAsset getMonsterAsset() {
        return monsterAsset;
    }
}