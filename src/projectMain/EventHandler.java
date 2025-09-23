package projectMain;

//import java.awt.*;

public class EventHandler {
    GamePanel gamePanel;
    EventRectangle[][] eventRectangle;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRectangle = new EventRectangle[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRectangle[col][row] = new EventRectangle();
            eventRectangle[col][row].x = 23;
            eventRectangle[col][row].y = 23;
            eventRectangle[col][row].width = 3;
            eventRectangle[col][row].height = 3;
            eventRectangle[col][row].eventRectangleDefaultX = eventRectangle[col][row].x;
            eventRectangle[col][row].eventRectangleDefaultY = eventRectangle[col][row].y;

            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            if (hit(14, 3, "right")) {
                damagePit(14, 3, gamePanel.dialogueState);
            }

            if (hit(3, 2, "left")) {
                healingPool(2, 1, gamePanel.dialogueState);
            }
            if (hit(8, 14, "left")) {
                teleportingPort(7, 13, gamePanel.dialogueState);
            }
        }
    }

    private void teleportingPort(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "Teleport!";
        //gamePanel.player.life -= 1;
        gamePanel.player.worldX = gamePanel.tileSize * 2;
        gamePanel.player.worldY = gamePanel.tileSize * 3;

    }

    private void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fell into pit!!";
        gamePanel.player.life -= 1;
        //eventRectangle[col][row].eventDone = true;
        canTouchEvent = false;
    }

    private void healingPool(int col, int row, int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.ui.currentDialogue = "You ate the Spicy Chicken \n It gave you a health and mana boost";
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.assetSetter.setMonster();
        }
    }

    public boolean hit(int col, int row, String requiredDirection) {
        boolean hit = false;
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
        eventRectangle[col][row].x = col * gamePanel.tileSize + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * gamePanel.tileSize + eventRectangle[col][row].y;

        if (gamePanel.player.solidArea.intersects(eventRectangle[col][row]) && !eventRectangle[col][row].eventDone) {
            if (gamePanel.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;

                previousEventX= gamePanel.player.worldX ;
                previousEventY= gamePanel.player.worldY ;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRectangle[col][row].x = eventRectangle[col][row].eventRectangleDefaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].eventRectangleDefaultY;

        return hit;
    }
}
