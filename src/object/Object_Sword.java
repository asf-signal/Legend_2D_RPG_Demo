package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Sword extends Entity {

    public Object_Sword(GamePanel gamePanel) {
        super(gamePanel);

        name = "Sword";
        down1 = setUp("/objectTiles/sword", gamePanel.tileSize, gamePanel.tileSize);
    }
}
