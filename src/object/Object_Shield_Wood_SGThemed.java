package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Shield_Wood_SGThemed extends Entity {
    public Object_Shield_Wood_SGThemed(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "SkaterGirl Themed Wooden Shield";
        down1 = setUp("/objectTiles/shield_w_sg", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 4;
        description = "["+name+"]\nMade by wood coated\nin hard plastic";

    }
}
