package main;

import entity.*;

import java.util.ArrayList;

public class CollisionHandler {
    Panel panel;

    public CollisionHandler(Panel panel) {
        this.panel = panel;
    }

    public void checkMapCollision(Entity entity) {
        int entityLeftCollision = entity.getPosX() + entity.getCollisionArea().x;
        int entityRightCollision = entityLeftCollision + entity.getCollisionArea().width;
        int entityTopCollision = entity.getPosY() + entity.getCollisionArea().y;
        int entityBottomCollision = entityTopCollision + entity.getCollisionArea().height;

        int tileNum1, tileNum2;
        switch (entity.getDirection()) {
            case "left":
                tileNum1 = panel.mapTile.getMapTileNum( (entityLeftCollision - entity.getSpeed()) / panel.tileSize,entityTopCollision / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( (entityLeftCollision - entity.getSpeed()) / panel.tileSize,entityBottomCollision / panel.tileSize);
                if(entityLeftCollision - entity.getSpeed() < 0 || tileNum1 != 0 || tileNum2 != 0) {
                    entity.detectCollision();
                }
                break;
            case "right":
                tileNum1 = panel.mapTile.getMapTileNum( (entityRightCollision + entity.getSpeed()) / panel.tileSize,entityTopCollision / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( (entityRightCollision + entity.getSpeed()) / panel.tileSize,entityBottomCollision / panel.tileSize);
                if((entityBottomCollision + entity.getSpeed()) / panel.tileSize >= panel.mapTile.getMapWidth() || tileNum1 != 0 || tileNum2 != 0) {
                    entity.detectCollision();
                }
                break;
            case "up":
                tileNum1 = panel.mapTile.getMapTileNum( entityLeftCollision / panel.tileSize,(entityTopCollision - entity.getSpeed()) / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( entityRightCollision / panel.tileSize,(entityTopCollision - entity.getSpeed()) / panel.tileSize);
                if(entityTopCollision - entity.getSpeed() < 0 || tileNum1 != 0 || tileNum2 != 0) {
                    entity.detectCollision();
                }
                break;
            case "down":
                tileNum1 = panel.mapTile.getMapTileNum( entityLeftCollision / panel.tileSize,(entityBottomCollision + entity.getSpeed()) / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( entityRightCollision / panel.tileSize,(entityBottomCollision + entity.getSpeed()) / panel.tileSize);
                if((entityBottomCollision + entity.getSpeed()) / panel.tileSize >= panel.mapTile.getMapHeight() || tileNum1 != 0 || tileNum2 != 0) {
                    entity.detectCollision();
                }
                break;
        }
    }

    public void checkMonsterCollision(Entity entity) {
        int entityLeftCollision = entity.getPosX() + entity.getCollisionArea().x;
        int entityRightCollision = entityLeftCollision + entity.getCollisionArea().width;
        int entityTopCollision = entity.getPosY() + entity.getCollisionArea().y;
        int entityBottomCollision = entityTopCollision + entity.getCollisionArea().height;

        ArrayList<Monster> monsters = panel.getMonsters();
//        System.out.println(Arrays.toString(monsters));
        for(Monster monster : monsters) {
            if(monster == null) continue;

            int monsterLeftCollision = monster.getPosX() + monster.getCollisionArea().x;
            int monsterRightCollision = monsterLeftCollision + monster.getCollisionArea().width;
            int monsterTopCollision = monster.getPosY() + monster.getCollisionArea().y;
            int monsterBottomCollision = monsterTopCollision + monster.getCollisionArea().height;

            switch (entity.getDirection()) {
                case "left":
                    if(checkMiddlePos(entityTopCollision, monsterTopCollision, monsterBottomCollision) || checkMiddlePos(entityBottomCollision, monsterTopCollision, monsterBottomCollision)) {
                        if (checkMiddlePos(entityLeftCollision - entity.getSpeed(), monsterLeftCollision, monsterRightCollision)) {
                            entity.detectCollision();
                            return;
                        }
                    }
                    break;
                case "right":
                    if(checkMiddlePos(entityTopCollision, monsterTopCollision, monsterBottomCollision) || checkMiddlePos(entityBottomCollision, monsterTopCollision, monsterBottomCollision)) {
                        if (checkMiddlePos(entityRightCollision + entity.getSpeed(), monsterLeftCollision, monsterRightCollision)) {
                            entity.detectCollision();
                            return;
                        }
                    }
                    break;
                case "up":
                    if(checkMiddlePos(entityLeftCollision, monsterLeftCollision, monsterRightCollision) || checkMiddlePos(entityRightCollision, monsterLeftCollision, monsterRightCollision)) {
                        if (checkMiddlePos(entityTopCollision - entity.getSpeed(), monsterTopCollision, monsterBottomCollision)) {
                            entity.detectCollision();
                            return;
                        }
                    }
                    break;
                case "down":
                    if(checkMiddlePos(entityLeftCollision, monsterLeftCollision, monsterRightCollision) || checkMiddlePos(entityRightCollision, monsterLeftCollision, monsterRightCollision)) {
                        if (checkMiddlePos(entityBottomCollision + entity.getSpeed(), monsterTopCollision, monsterBottomCollision)) {
                            entity.detectCollision();
                            return;
                        }
                    }
                    break;
            }
        }
    }

    public boolean checkSpawn(int posX, int posY, int monsterSize) {
        int tileX = posX / panel.tileSize;
        int tileY = posY / panel.tileSize;
        posX = tileX * panel.tileSize;
        posY = tileY * panel.tileSize;
        for(int i = 0; i < monsterSize; ++i)
            for(int j = 0; j < monsterSize; ++j) {
                int tileNum = panel.mapTile.getMapTileNum(posX + i, posY + j);
                if(tileNum != 0)
                    return false;
            }
        return true;
    }

    private boolean checkMiddlePos(int pos, int low, int high) {
        return (pos > low && pos < high);
    }
}