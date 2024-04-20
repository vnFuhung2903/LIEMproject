package map;

import java.awt.Graphics2D;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.json.*;
import java.util.Base64;

import main.Panel;

public class TileManage  {
    Panel panel;
    Tile[] tile;
    int[][] mapTileNum;
    final int MARGIN = 100;

    JSONObject mapObj;

    public TileManage(Panel panel) {
        this.panel = panel;
        tile = new Tile[100];

        mapTileNum = new int[100][100];
//        loadMap("assets/mapDesert/map.txt");
        loadMapTest("assets/mapDesert1/mapdeserttest.json");

        getTileImageTest();
//        getTileImage();
    }
    public void getTileImageTest() {
        try {
            JSONArray tilesets = mapObj.getJSONArray("tilesets");

            for(int i = 0; i < tilesets.length(); ++i) {
                int index = tilesets.getJSONObject(i).getInt("firstgid");
                String Image = tilesets.getJSONObject(i).getString("image");
                tile[index] = new Tile();
                tile[index].image = ImageIO.read(new File("assets/mapDesert1/" + Image));
            }

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            for (int i = 1; i <= 3;i++) {

                tile[i] = new Tile();
                tile[i].image = ImageIO.read(new File("assets/mapDesert/tileDesert-0" + i + ".png"));

            }
            tile[31] = new Tile();
            tile[31].image = ImageIO.read(new File("assets/mapDesert/boneDesert-01.png"));
            tile[32] = new Tile();
            tile[32].image = ImageIO.read(new File("assets/mapDesert/rockDesert-01.png"));
            for (int i = 11; i <= 15;i++) {

                tile[i] = new Tile();
                tile[i].image = ImageIO.read(new File("assets/mapDesert/barrierMapDesert-0" + (i-10) +".png"));

            }
            for (int i = 21; i <= 22;i++) {

                tile[i] = new Tile();
                tile[i].image = ImageIO.read(new File("assets/mapDesert/treeDesert-0" + (i-20) + ".png"));

            }
//            for (int i = 31; i <= 39;i++) {
//
//                tile[i] = new Tile();
//                tile[i].image = ImageIO.read(new File("assets/mapDesert/cat-0" + (i-30) + ".png"));
//
//            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMapTest(String map) {
        try {
            mapObj = new JSONObject(new JSONTokener(new FileReader(map)));
            JSONArray layers = mapObj.getJSONArray("layers");
            JSONArray data = layers.getJSONObject(0).getJSONArray("data");
            int height = layers.getJSONObject(0).getInt("height");
            for(int row = 0; row < height; ++row) {
                for (int col = 0; col < height; ++col) {
                    mapTileNum[row][col] = data.getInt(row * panel.maxMapCol + col);
                    System.out.print(mapTileNum[row][col]);
                    System.out.print(", ");
                }
                System.out.println();
            }
//            System.out.println(data.get(0));
        } catch (FileNotFoundException e) {
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
                    mapTileNum[row][col] = num;
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
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
                        g2.drawImage(tile[tileNum].image, mapX - playerX + screenX, mapY - playerY + screenY, panel.tileSize + 1, panel.tileSize + 1, null);

                    }
//                    else if (tileNum > 100 & tile[tileNum] != null) {
//                        g2.drawImage(tile[tileNum].image, mapX - playerX + screenX, mapY - playerY + screenY, panel.tileSize*3, panel.tileSize*2, null);
//
//                    }
//                    else if (tileNum != 0 && tile[tileNum] != null) {
//                        g2.drawImage(tile[tileNum].image, mapX - playerX + screenX, mapY - playerY + screenY, panel.tileSize, panel.tileSize, null);
//                    }
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
}