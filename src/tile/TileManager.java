package tile;

import projectMain.GamePanel;
import projectMain.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile = new Tile[10];
//    public Map<Integer, Tile> tiles = new HashMap(10);

    public int[][] mapTileNum = new int[256][88];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        loadTileImage();
        loadMap("/maps/world01.txt");
    }

    public void loadTileImage() {
           /*tiles.put(0, new Tile("/backgroundTiles/waterTile.png"));
            tiles.put(1, new Tile("/backgroundTiles/grassTile.png")); //...*/

           /* tile[0] = new Tile("/backgroundTiles/waterTile.png");
            tile[1] = new Tile("/backgroundTiles/grassTile.png");
            tile[2] = new Tile("/backgroundTiles/wallBrickTile.png");
            tile[2].collision = true;
            tile[3] = new Tile("/backgroundTiles/treeTile.png");
            tile[3].collision = true;
            tile[4] = new Tile("/backgroundTiles/sandTile.png");
            tile[5] = new Tile("/backgroundTiles/dirtTile.png");
            tile[6] = new Tile("/backgroundTiles/gravelTile.png");*/
        setUp(0, "waterTile",true);
        setUp(1, "grassTile",false);
        setUp(2, "wallBrickTile",true);
        setUp(3, "treeTile",true);
        setUp(4, "sandTile",false);
        setUp(5, "dirtTile",false);
        setUp(6, "gravelTile",false);
        setUp(7, "blackVoid",true);
        //setUp(8, "treeTrunk",true);
    }
    public void setUp(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try{
        tile[index] = new Tile();
        tile[index].image = ImageIO.read(getClass().getResourceAsStream("/backgroundTiles/" + imageName + ".png"));
        tile[index].image = utilityTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
        tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();
                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;

                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

                graphics2D.drawImage(tile[tileNum].image, screenX, screenY, null);
                /*graphics2D.drawImage(tile[tileNum].image,
                        screenX, screenY,
                        gamePanel.tileSize,
                        gamePanel.tileSize,
                        null);*/
            }
            graphics2D.drawImage(tile[tileNum].image, screenX, screenY, null);
            /*graphics2D.drawImage(tile[tileNum].image,
                    screenX, screenY,
                    gamePanel.tileSize,
                    gamePanel.tileSize,
                    null);*/
            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
