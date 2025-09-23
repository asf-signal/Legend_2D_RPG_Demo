package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Bed extends Entity {
    public Object_Bed(GamePanel gamePanel) {
        super(gamePanel);

        name = "Bed";
        down1 = setUp("/objectTiles/bed", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
    }
}
