package projectMain;

import Monster.BigSnake;
import entity.NPC_SkaterGirl;
import interactiveTile.InteractiveTile_DryTree;
import object.*;

public class  AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        int i = 0;
        gamePanel.obj[i] = new Object_Treasure(gamePanel);
        gamePanel.obj[i].worldX = 14 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 12 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Door(gamePanel);
        gamePanel.obj[i].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 2 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Bed(gamePanel);
        gamePanel.obj[i].worldX = 2 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 2 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Key(gamePanel);
        gamePanel.obj[i].worldX = 2 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 8 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Key(gamePanel);
        gamePanel.obj[i].worldX = 14 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 13 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Ax_Normal(gamePanel);
        gamePanel.obj[i].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 4 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Objctect_GoldenCoin(gamePanel);
        gamePanel.obj[i].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 5 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Shield_Wood_SGThemed(gamePanel);
        gamePanel.obj[i].worldX = 11 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 5 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Potion(gamePanel);
        gamePanel.obj[i].worldX = 11 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 4 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_Heart(gamePanel);
        gamePanel.obj[i].worldX = 12 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 4 * gamePanel.tileSize;
        i++;
        gamePanel.obj[i] = new Object_ManaCrystals(gamePanel);
        gamePanel.obj[i].worldX = 13 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 4 * gamePanel.tileSize;

    }

    public void setNPC() {
        gamePanel.npc[0] = new NPC_SkaterGirl(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 3;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 6;
    }

    public void setMonster() {
        int i = 0;
        gamePanel.monster[i] = new BigSnake(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 6;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 8;
        i++;
        gamePanel.monster[i] = new BigSnake(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 5;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 8;
        i++;
        gamePanel.monster[i] = new BigSnake(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 11;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 14;
        i++;
        gamePanel.monster[i] = new BigSnake(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 12;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 14;
    }

    public void setInteractiveTile() {
        int i = 0;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 14, 2);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 15, 2);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 16, 2);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 1, 10);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 2, 10);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 3, 10);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 4, 10);i++;
        gamePanel.interactiveTile[i] = new InteractiveTile_DryTree(gamePanel, 5, 10);i++;
    }
}
