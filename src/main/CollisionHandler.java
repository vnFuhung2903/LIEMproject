package main;

import entity.*;

public class CollisionHandler {
    Panel panel;

    public CollisionHandler(Panel panel) {
        this.panel = panel;
    }

    public void checkTileCollision(Entity entity) {
        int entityLeftCollision = entity.getPosX() + entity.getCollisionArea().x;
        int entityRightCollision = entityLeftCollision + entity.getCollisionArea().width;
        int entityTopCollision = entity.getPosY() + entity.getCollisionArea().y;
        int entityBottomCollision = entityTopCollision + entity.getCollisionArea().height;

        int tileNum1, tileNum2;
        switch (entity.getDirection()) {
            case "left":
                tileNum1 = panel.mapTile.getMapTileNum( (entityLeftCollision - entity.getSpeed()) / panel.tileSize,entityTopCollision / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( (entityLeftCollision - entity.getSpeed()) / panel.tileSize,entityBottomCollision / panel.tileSize);
                if(panel.mapTile.getTile(tileNum1).collision || panel.mapTile.getTile(tileNum2).collision) {
                    entity.detectCollision();
                }
                break;
            case "right":
                tileNum1 = panel.mapTile.getMapTileNum( (entityRightCollision + entity.getSpeed()) / panel.tileSize,entityTopCollision / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( (entityRightCollision + entity.getSpeed()) / panel.tileSize,entityBottomCollision / panel.tileSize);
                if(panel.mapTile.getTile(tileNum1).collision || panel.mapTile.getTile(tileNum2).collision) {
                    entity.detectCollision();
                }
                break;
            case "up":
                tileNum1 = panel.mapTile.getMapTileNum( entityLeftCollision / panel.tileSize,(entityTopCollision - entity.getSpeed()) / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( entityRightCollision / panel.tileSize,(entityTopCollision - entity.getSpeed()) / panel.tileSize);
                if(panel.mapTile.getTile(tileNum1).collision || panel.mapTile.getTile(tileNum2).collision) {
                    entity.detectCollision();
                }
                break;
            case "down":
                tileNum1 = panel.mapTile.getMapTileNum( entityLeftCollision / panel.tileSize,(entityBottomCollision + entity.getSpeed()) / panel.tileSize);
                tileNum2 = panel.mapTile.getMapTileNum( entityRightCollision / panel.tileSize,(entityBottomCollision + entity.getSpeed()) / panel.tileSize);
                if(panel.mapTile.getTile(tileNum1).collision || panel.mapTile.getTile(tileNum2).collision) {
                    entity.detectCollision();
                }
                break;
        }
    }
}
