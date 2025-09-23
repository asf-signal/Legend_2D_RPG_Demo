package object;

import entity.Entity;
import projectMain.GamePanel;

public class Object_ManaCrystals extends Entity {
    GamePanel gamePanel;
    public Object_ManaCrystals(GamePanel gamePanel){
        super(gamePanel);
        this.gamePanel=gamePanel;

        name="Mana Crystal";
        type = type_pickUpOnly;
        value = 1;
        down1  = setUp("/objectTiles/manacrystal_full",gamePanel.tileSize,gamePanel.tileSize);
        image=setUp("/objectTiles/manacrystal_full",gamePanel.tileSize,gamePanel.tileSize);
        image2=setUp("/objectTiles/manacrystal_blank",gamePanel.tileSize,gamePanel.tileSize);
    }
    public void use(Entity entity){
        gamePanel.ui.addMessage("Mana + " + value);
        entity.mana  += value;
    }
}
