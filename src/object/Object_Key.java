package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Key extends Entity  {
    public Object_Key(GamePanel gamePanel) {
        super(gamePanel);

        name = "Key";
        down1 = setUp("/objectTiles/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "["+name+"]\nOpens doors";
    }
}
