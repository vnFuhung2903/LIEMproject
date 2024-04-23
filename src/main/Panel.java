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
    final int scale = 3;
    final public int tileSize = originalTileSize * scale ; // 48*48 tile
    final public int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    final public int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;


    // MAP SETTINGS
    final public int maxMapCol = 250;
    final public int maxMapRow = 250;
    final public int mapWidth = tileSize * maxMapRow;
    final public int mapHeight = tileSize * maxMapCol;

    // FPS
    double FPS = 60;

    // Systems
    TileManage mapTile = new TileManage(this);
    Nightmode nightmode = new Nightmode(this);
    KeyHandler keyHandler = new KeyHandler();
    MouseEventHandler mouseEventHandler = new MouseEventHandler();
    Thread gameThread;
    public CollisionHandler collisionHandler = new CollisionHandler(this);
//    AssetSetter assetSetter = new AssetSetter(this);

    // Entities
    entity.Character player = new entity.characters.Witch(this, 10, keyHandler, mouseEventHandler);
    int numMonsters = 10, numItems = 0, numEffects = 0;
    Monster[] monsters = new Monster[numMonsters];
    Item[] items = new Item[numItems];
    Effect[] effects = new Effect[numEffects];
    ArrayList<Entity> entityList = new ArrayList<>();
//    ArrayList<Skill> skillList = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<Effect> effectList = new ArrayList<>();

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

        ArrayList<Monster> monsterList = new ArrayList<>(Arrays.asList(monsters));
        monsterList.removeIf(monster -> monster.getHp() <= 0);
        numMonsters = monsterList.size();
        monsters = monsterList.toArray(new Monster[numMonsters]);
        monsterList.clear();

        for(Monster monster : monsters) {
            monster.update();
        }

        for(Item item : items) {
            if(item != null) {
                item.update();
            }
        }

        for(Effect effect : effects) {
            if(effect != null) {
                effect.update();
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

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Draw map
        mapTile.draw(g2);

        for (Monster monster : monsters) {
            if (monster != null) {
                entityList.add(monster);
            }
        }

//        for (Skill skill : skillList) {
//            if (skill != null) {
//                skillList.add(skill);
//            }
//        }
        entityList.add(player);

        // Sort entities in posY
        entityList.sort(Comparator.comparingInt(Entity::getPosY));

        // Draw others
        for (Entity entity : entityList) {
            entity.draw(g2);
        }

        for(Item item : items) {
            item.draw(g2);
        }

        for(Effect effect : effects) {
            effect.draw(g2);
        }

//        nightmode.draw(g2);

        entityList.clear();
        g2.dispose();
    }

    void setMonsters() {

        for(int i = 0; i < numMonsters; ++i) {
            boolean created = false;
            while (!created) {
                Random randomX = new Random();
                Random randomY = new Random();
                int x = randomX.nextInt(mapWidth) + 1;
                int y = randomY.nextInt(mapHeight) + 1;

                if(collisionHandler.checkSpawn(x, y, 1)) {
                    monsters[i] = new Slime(this, 1, 10);
                    monsters[i].setPosX(x);
                    monsters[i].setPosY(y);
                    System.out.print(x);
                    System.out.println(y);
                    created = true;
                }
            }
        }
    }

    public void createItem(int id, int posX, int posY) {
        itemList = new ArrayList<>(Arrays.asList(items));
        itemList.add(new Item(this, 2, posX, posY));
        numItems = itemList.size();
        items = itemList.toArray(new Item[numItems]);
        itemList.clear();
    }

    public void collectItem() {
        itemList = new ArrayList<>(Arrays.asList(items));
        itemList.removeIf(Objects::isNull);
        itemList.removeIf(item -> !item.isCollectable());
        numItems = itemList.size();
        items = itemList.toArray(new Item[numItems]);
        itemList.clear();
    }

    public void setEffect(Entity entity, String name, int interval, int entitySize) {
        for(Effect effect : effects) {
            if(effect.getEntity() == entity && Objects.equals(effect.getName(), name)) {
                effect.extend(interval);
                return;
            }
            System.out.println(effect);
        }
        effectList = new ArrayList<>(Arrays.asList(effects));
        effectList.add(new Effect(this, entity, name, interval, entitySize));
        numEffects = effectList.size();
        effects = effectList.toArray(new Effect[numEffects]);
        System.out.println(Arrays.toString(effects));
        effectList.clear();
    }

    public Monster[] getMonsters() { return monsters; }

    public Character getPlayer() { return player; }
}