package object;

import entity.Entity;
import projectMain.GamePanel;
public class Object_Door extends Entity {
    public Object_Door(GamePanel gamePanel) {

        super(gamePanel);

        name = "Door";
        down1 = setUp("/objectTiles/door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
