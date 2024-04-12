package map;

import java.awt.Graphics2D;
import java.io.*;
import java.util.Objects;
import javax.imageio.ImageIO;

import main.Panel;

public class TileManage  {
    Panel panel;
    Tile[] tile;

    int mapTileNum[][];

    public TileManage(Panel panel) {
        this.panel = panel;
        tile = new Tile[50];
        getTileImage3();

        mapTileNum = new int[panel.maxMapCol][panel.maxMapRow];
        loadMap("assets/mapDesert/map.txt");
    }
    public void getTileImage () {
        try {


            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/glass1.png")));
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/rock1.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/water.png")));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/water.png")));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/glass3.png")));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/rock2.png")));


        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void getTileImage2 () {
        try {


            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/Untitled-14-01.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/Untitled-14-02.png")));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/Untitled-14-03.png")));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/Untitled-14-04.png")));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/Untitled-14-05.png")));
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("../assets/map/Untitled-14-03.png")));
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void getTileImage3() {
        try {
            for (int i = 1; i <= 9;i++) {

                tile[i] = new Tile();
                tile[i].image = ImageIO.read(new File("assets/mapDesert/mapDesert-0" + i +".png"));

            }
            for (int i = 10; i <= 30;i++) {

                tile[i] = new Tile();
                tile[i].image = ImageIO.read(new File("assets/mapDesert/mapDesert-" + i + ".png"));

            }
            for (int i = 31; i <= 39;i++) {

                tile[i] = new Tile();
                tile[i].image = ImageIO.read(new File("assets/mapDesert/cat-0" + (i-30) + ".png"));

            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void loadMap(String map) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(map)));

            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < panel.maxMapRow) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < panel.maxMapCol && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;

        while (col < panel.maxMapCol && row < panel.maxMapRow) {
            int tileNum = mapTileNum[col][row];
            int MapX = col * panel.tileSize;
            int MapY = row * panel.tileSize;
            int screenX = MapX - panel.player.posX + panel.player.screenX;// hoi kho hieu
            int screenY = MapY - panel.player.posY + panel.player.screenY;
            if (MapX > panel.player.posX - panel.player.screenX - 100 &&
                    MapX < panel.player.posX + panel.player.screenX + 100 &&
                    MapY > panel.player.posY - panel.player.screenY - 100 &&
                    MapY < panel.player.posY + panel.player.screenY + 100
            ) {
                if(tile[tileNum] != null) {
                    g2.drawImage(tile[tileNum].image, screenX,screenY, panel.tileSize, panel.tileSize, null);
//                    System.out.println("sX: " +screenX +" sY: " + screenY);
                } else {
                    g2.drawImage(tile[1].image, screenX,screenY, panel.tileSize, panel.tileSize, null);
                }
            }
            col++;
            if (col == panel.maxMapCol) {
                col=0;
                row++;

            }

        }

    }
}
