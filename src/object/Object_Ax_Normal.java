package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Ax_Normal extends Entity {
    public Object_Ax_Normal(GamePanel gamePanel){
        super(gamePanel);

        type = type_ax;
        name = "Woodcutter's Ax";
        down1 = setUp("/objectTiles/ax",gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 4;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Woodcutter's Ax]\nA but still can cut\nsome trees.";
    }
}
