package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Sword_Normal extends Entity {

    public Object_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);

        type = type_sword;
        name = "Sword";
        down1 = setUp("/objectTiles/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 4;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "["+name+"]\nAn old sword";
    }
}
