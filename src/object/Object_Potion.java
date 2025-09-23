package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_Potion extends Entity {
    GamePanel gamePanel;
    public Object_Potion(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel=gamePanel;

        type = type_consumable;
        name="red potion";
        value=5;
        down1=setUp("/objectTiles/potion",gamePanel.tileSize,gamePanel.tileSize);
        description="[red potion] Increase your life by "+value;
    }
    public void use(Entity entity){
        gamePanel.gameState=gamePanel.dialogueState;
        gamePanel.ui.currentDialogue="You drink the "+name+"!\n"
                + "Your life has recovered by"+value+"!";
        entity.life+=value;
    }
}
