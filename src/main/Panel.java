package main;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.*;
import java.util.*;

import entity.*;
import entity.Character;
import entity.monsters.*;
import entity.skills.witch.WitchQStun;
import map.*;

public class Panel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16*16 tile
    final int scale = 4;
    final public int tileSize = originalTileSize * scale ; // 48*48 tile
    final public int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    final public int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;


    // MAP SETTINGS


    public int timeNight = 2000;
    final public int maxMapCol = 250;
    final public int maxMapRow = 250;
    final public int mapWidth = tileSize * maxMapRow;
    final public int mapHeight = tileSize * maxMapCol;

    // FPS

    double FPS = 60;

    // GAME STATE
    public enum gameState {
        startState,
        ingameState,
        pauseState,
        guideState
    };
    gameState currentState;
    String[] startOption = {"start", "guide"};
    String[] pauseOption = {"back", "quit", "mute"};
    int currentPointer = 0;
    boolean bossFighter = true;

    // UI
    boolean night = false;
    public boolean openItem = false;
    public int pointerItem = 0;
    int bossId;

    // Systems
    TileManage mapTile = new TileManage(this);
    SandTrap sandTrap;
    SpiderCave spiderCave;
    KeyHandler keyHandler = new KeyHandler(this);
    MouseEventHandler mouseEventHandler = new MouseEventHandler();
    Thread gameThread;
    UI ui = new UI(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public Sound sound = new Sound();

    // Entities
    entity.Character player = new entity.characters.Witch(this, 10, keyHandler, mouseEventHandler);
    ArrayList<Monster> monsters = new ArrayList<>();
    ArrayList<Skill> skillList = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Effect> effects = new ArrayList<>();
    Asset asset = new Asset(this);
    Monster boss;
    ArrayList<WitchQStun> witchSkillEffects = new ArrayList<>();

    // Items
    public int numItemHealHp = 0;
    public int numItemHealMana = 0;
    public int numItemImmunity = 0;

    public Panel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(screenSize.width,screenSize.height));
        setBgColor(243, 174, 92);

        // in some cases, using double buffered my increase resource usage and latency (tang tai nguyen va do tre)
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseEventHandler);
        this.setFocusable(true); // receive keyboard and input events
        setUpGame();
    }

    public void setUpGame() {
        currentState = gameState.startState;
        setMap();
        setMonsters();
        sound.startMusic.loop(Clip.LOOP_CONTINUOUSLY);
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
        if(currentState != gameState.ingameState) {
            return;
        }

        useItem(keyHandler.getItemPressed());

        if(sandTrap != null) sandTrap.update();
        if(boss != null) boss.update();
        else if(bossFighter) {
            Random random = new Random();
            bossId = random.nextInt() % 2;
            switch(bossId) {
                case 0:
                    boss = new Skeleton(this, 5, 10);
                    break;
                case 1:
                    boss = new Ghost(this, 5, 10);
                    break;
            }
            boss.setPosX(mapWidth / 2);
            boss.setPosY(mapHeight / 2);
            monsters.add(boss);
            sound.igMusic.close();
            sound.bossMusic.loop(Clip.LOOP_CONTINUOUSLY);
            bossFighter = false;
        }

        monsters.removeIf(monster -> monster.getHp() <= 0);
        for(Monster monster : monsters) {
            if(monster == boss) continue;
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

        witchSkillEffects.removeIf(effect -> !effect.isActive());
        for(WitchQStun effect : witchSkillEffects) {
            effect.update();
        }

        skillList.removeIf(skill -> !skill.isCasted());
        for(Skill skill : skillList) {
            if(skill != null) {
                skill.update();
            }
        }

        player.update();
    }
    void checkNight() {
       if(--timeNight < 0 ){
           night = !night;
           timeNight = 2000;
       }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        if(currentState == gameState.ingameState || currentState == gameState.pauseState) {

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

            for (Item item : items) {
                item.draw(g2);
            }

            for (Effect effect : effects) {
                effect.draw(g2);
            }

            for (WitchQStun effect : witchSkillEffects) {
                effect.draw(g2);
            }

            for (Skill skill : skillList) {
                skill.draw(g2);
            }
        }

        ui.draw(g2);
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
    public void spawnSlave (int x,int y) {
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

//        for(int i = 0; i < 1; ++i) {
//            boolean created = false;

//            while (!created) {
//                Random randomX = new Random();
//                Random randomY = new Random();
//                int x = randomX.nextInt(mapWidth);
//                int y = randomY.nextInt(mapHeight);

//
//                if(collisionHandler.checkSpawn(x, y, 1)) {
//                    Ghost monster = new Ghost(this, 5, 10);
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

    public void createItem(int posX, int posY) {
        Random random = new Random();
        int idx = random.nextInt(3);
        items.add(new Item(this, idx, posX, posY));
    }

    public void setWitchSkillEffects(Monster monster, int time) {
        for(WitchQStun effect : witchSkillEffects) {
            if(effect.getMonster() == monster) {
                return;
            }
        }
        witchSkillEffects.add(new WitchQStun(this, monster, time));
    }

    public void setEffect(Entity entity, String name, int time, int entitySize) {
        for(Effect effect : effects) {
            if(effect.getEntity() == entity && Objects.equals(effect.getName(), name)) {
                return;
            }
        }
        effects.add(new Effect(this, entity, name, time, entitySize));
    }

    public void useItem(int id) {
        switch (id) {
            case 0:
                if(numItemHealHp > 0) {
                    setEffect(player, "healing", 10, 2);
                    --numItemHealHp;
                }
                break;
            case 1:
                if(numItemHealMana > 0) {
                    setEffect(player, "healingMana", 10, 2);
                    --numItemHealMana;
                }
                break;
            case 2:
                if(numItemImmunity > 0) {
                    setEffect(player, "immune", 10, 2);
                    --numItemImmunity;
                }
                break;
            default:
                break;
        }
    }

    public int getBossHpPercent () {
        return boss.getHp() * 100 / boss.getMaxHp();
    }

    public int getHpPercent() { return player.getHp() * 100 / player.getMaxHp(); }
    public int getManaPercent() {
        return player.getMana() * 100 / player.getMaxMana();
    }


    public void setSkill(Skill skill) {
        skillList.add(skill);
    }

    public ArrayList<Monster> getMonsters() { return monsters; }

    public Character getPlayer() { return player; }

    public Asset getAsset() {
        return asset;
    }

    void setBgColor (int r, int g, int b) {
        Color color = new Color(r, g, b);
        this.setBackground(color);
    }

    public gameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(gameState currentState) {
        this.currentState = currentState;
    }

    public boolean encounterBoss() {
        return boss != null;
    }

    public String getCurrentOption(String[] option) {
        return option[currentPointer];
    }

    public int getCurrentPointer() {
        return currentPointer;
    }

    public void modifyPointer(int value, String[] option) {
        this.currentPointer += value;
        if(currentPointer < 0) currentPointer = option.length - 1;
        if(currentPointer >= option.length) currentPointer = 0;
    }

    public void resetCurrentPointer() { this.currentPointer = 0; }

    public int getBossId() {
        return bossId;
    }
}