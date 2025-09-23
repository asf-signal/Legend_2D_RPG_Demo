package object;

import entity.Entity;
import projectMain.GamePanel;
public class Object_Treasure extends Entity {
    public Object_Treasure(GamePanel gamePanel) {

        super(gamePanel);

        name = "Treasure";
        down1 = setUp("/objectTiles/treasure", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
    }
}
