package interactiveTile;

import projectMain.GamePanel;

public class Interactive_TreeTrunk extends InteractiveTile {
    GamePanel gamePanel;

    public Interactive_TreeTrunk(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel=gamePanel;

        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setUp("/interactiveTiles/treeTrunk", gamePanel.tileSize, gamePanel.tileSize);
        //destructible = true;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
