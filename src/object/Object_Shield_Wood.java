package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Shield_Wood extends Entity {

    public Object_Shield_Wood(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Wooden Shield";
        down1 = setUp("/objectTiles/shield_w", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 3;
        description = "["+name+"]\nMade by wood";

    }
}
