package interactiveTile;

import entity.Entity;
import projectMain.GamePanel;

import java.awt.*;

public class InteractiveTile_DryTree extends InteractiveTile {
    GamePanel gamePanel;

    public InteractiveTile_DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel=gamePanel;

        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setUp("/interactiveTiles/dryTree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        //return entity.currentWeapon.type == entity.currentWeapon.type_ax;
        switch (entity.currentWeapon.type) {
            case type_ax: isCorrectItem = true; break;
            case type_sword: isCorrectItem = false; break;
        }
        return isCorrectItem;
    }
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new Interactive_TreeTrunk(gamePanel, worldX/gamePanel.tileSize, worldY /gamePanel.tileSize);
        return tile;
    }

}
