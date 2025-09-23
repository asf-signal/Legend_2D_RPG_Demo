package object;

import entity.Entity;
import projectMain.GamePanel;

public class Objctect_GoldenCoin extends Entity {
    GamePanel gamePanel;
    public Objctect_GoldenCoin(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickUpOnly;
        name = "Gold Coin";
        value=1 ;
        down1 = setUp("/objectTiles/goldCoin", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void use(Entity entity){
        //gamePanel.gameState=gamePanel.dialogueState;
        gamePanel.ui.addMessage("Gold Coin + " + value);
        //gamePanel.ui.currentDialogue="Gold Coin + " + value;
        gamePanel.player.coin += value;
    }
}
