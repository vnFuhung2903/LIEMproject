package map;

import java.awt.Graphics2D;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;
import org.json.*;

import main.Panel;

public class TileManage  {
    Panel panel;
    Tile[] tile;
    int[][] mapTileNum;
    final int MARGIN = 100;
    int mapWidth = 1000, mapHeight = 1000;

    JSONObject mapObj, imageObj;

    public TileManage(Panel panel) {
        this.panel = panel;
        tile = new Tile[250];

        mapTileNum = new int[250][250];
        loadMap();

        getTileImage();
    }
    public void getTileImage() {
        try {
            imageObj = new JSONObject(new JSONTokener(new FileReader("assets/mapDesert/image.tsj")));
            JSONArray tiles = imageObj.getJSONArray("tiles");

            for(int i = 0; i < tiles.length(); ++i) {
                int index = tiles.getJSONObject(i).getInt("id");
                String Image = tiles.getJSONObject(i).getString("image");
                tile[index + 1] = new Tile();
                tile[index + 1].image = ImageIO.read(new File("assets/mapDesert/" + Image));
            }

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
//            String map;
//            Random random = new Random();
//            int r = random.nextInt(4);
//            map = "assets/mapDesert/desert" + (r + 1) + ".json";
            mapObj = new JSONObject(new JSONTokener(new FileReader("assets/mapDesert/map.json")));
            JSONArray layers = mapObj.getJSONArray("layers");
            JSONArray data = layers.getJSONObject(0).getJSONArray("data");
            mapWidth = layers.getJSONObject(0).getInt("width");
            mapHeight = layers.getJSONObject(0).getInt("height");
            for(int row = 0; row < mapWidth; ++row) {
                for (int col = 0; col < mapHeight; ++col) {
                    mapTileNum[row][col] = data.getInt(row * mapWidth + col);
//                    System.out.print(mapTileNum[row][col]);
//                    System.out.print(", ");
                }
//                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {
        int playerX = panel.getPlayer().getPosX();
        int playerY = panel.getPlayer().getPosY();
        int screenX = panel.getPlayer().screenX;
        int screenY = panel.getPlayer().screenY;

        int minX = playerX - screenX - MARGIN;
        int maxX = playerX + screenX + MARGIN;
        int minY = playerY - screenY - MARGIN;
        int maxY = playerY + screenY + MARGIN;


        for (int row = 0; row < panel.maxMapRow; row++) {
            for (int col = 0; col < panel.maxMapCol; col++) {
                int mapX = col * panel.tileSize;
                int mapY = row * panel.tileSize;

                if (isInDisplayArea(mapX, mapY, minX, minY, maxX, maxY)) {
                    int tileNum = mapTileNum[row][col];
                    if (tileNum >= 0 && tileNum < 100 & tile[tileNum] != null) {
                        g2.drawImage(tile[tileNum].image, mapX - playerX + screenX, mapY - playerY + screenY, panel.tileSize, panel.tileSize, null);

                    }
                }
            }
        }
    }

    private boolean isInDisplayArea(int mapX, int mapY, int minX, int minY, int maxX, int maxY) {
        return mapX >= minX && mapX <= maxX && mapY >= minY && mapY <= maxY;
    }

    public int getMapTileNum(int x, int y) {
        if(x >= 0 && y >= 0 && x < panel.maxMapCol && y < panel.maxMapRow)
            return mapTileNum[y][x];
        return 0;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}