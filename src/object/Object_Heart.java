package object;

import entity.Entity;
import projectMain.GamePanel;
public class Object_Heart extends Entity {
    GamePanel gamePanel;
    public Object_Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Heart";
        type = type_pickUpOnly;
        value = 2;
        down1 = setUp("/objectTiles/heartFull", gamePanel.tileSize, gamePanel.tileSize);
        image = setUp("/objectTiles/heartFull", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setUp("/objectTiles/heartHalf", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setUp("/objectTiles/heartBlank", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void use(Entity entity){
        gamePanel.ui.addMessage("Life + " + value);
        entity.life += value;
    }
}
