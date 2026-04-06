package projectMain;

import entity.Entity;
import entity.Player;
import interactiveTile.InteractiveTile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTitlesSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTitlesSize * scale; //48*48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 13;
    public final int screenWidth = tileSize * maxScreenCol; //760 pixels
    public final int screeHeight = tileSize * maxScreenRow; //576 pixels
    //full screen
    public int screenWidth2 = screenWidth; //760 pixels
    public int screeHeight2 = screeHeight;
    BufferedImage tempScreen; 
    Graphics2D graphics2D;
    //WORLD SETTINGS

    public final int maxWorldCol = 18;
    public final int maxWorldRow = 16;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    final int FPS = 60;
    Thread gameThread;
    //SYSTEM
    final TileManager tileManager = new TileManager(this);
    public final KeyHandler keyHandler = new KeyHandler(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);

    //ENTITY & OBJ
    public final Player player = new Player(this, keyHandler);
    public Entity[] obj = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    public InteractiveTile[] interactiveTile = new InteractiveTile[50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();


    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;

    int playerXCoordinate = 100;
    int playerYCoordinate = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screeHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true); //Sets the focusable state of this
        // Component to the specified value. Helps Game Panel focused to receive key input.
    }

    public void setUpGame() {
        assetSetter.setObjects();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screeHeight, BufferedImage.TYPE_INT_ARGB);//creates a blank buff image
        graphics2D = (Graphics2D)tempScreen.getGraphics();
        //setFullScreen();
    }
    public void setFullScreen(){
        //get local screen device
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(Main.window);

        //get full screen width and height
        screenWidth2 = Main.window.getWidth();
        screeHeight2 = Main.window.getHeight();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawingInterval = 1000000000 / FPS; //1 sec/60, .01666 secs
        double nextDrawingTime = System.nanoTime() + drawingInterval;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta +=(currentTime-lastTime)/drawingInterval;
            timer += (currentTime-lastTime);
            lastTime=currentTime;

            if (delta>=1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if (timer>= 1000000000){
                drawCount=0;
                timer=0;
            }
        }
    }

    //Check Pause thing later, it doesn't work, no errors,but no stop, V:13
    public void update() {
        if (gameState == playState) {
            //player
            player.update();
            //npc
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            //monster
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if (!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
            for (InteractiveTile tile : interactiveTile) {
                if (tile != null) {
                    tile.update();
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {

                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState) {
            //nothing
        }
    }

    public void drawToTempScreen() {
        long drawStart = 0;
        if (keyHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, screenWidth, screeHeight);
        //title screen
        if (gameState == titleState) {
            ui.draw(graphics2D);
        }
        //other
        else {
            //tile
            tileManager.draw(graphics2D);
            //interactive tile
//           /*for (InteractiveTile tile : interactiveTile) {
//               if (tile != null) {
//                   tile.draw(graphics2D);
//               }
//           }*/
            for (InteractiveTile tile : interactiveTile) {
                if (tile != null) {
                    tile.draw(graphics2D);
                }
            }
            //draw entities
            entityList.add(player);
            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity value : projectileList) {
                if (value != null) {
                    entityList.add(value);
                }
            }
            for (Entity value : particleList) {
                if (value != null) {
                    entityList.add(value);
                }
            }
            //SORT
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity entity1, Entity entity2) {
                    return Integer.compare(entity1.worldY, entity2.worldY);
                }
            });
            //draw entities
            for (Entity entity : entityList) {
                entity.draw(graphics2D);
            }
            //empty entities
            entityList.clear();
            //UI
            ui.draw(graphics2D);
        }
        //debug, for rendering, not sure If I'll do it yet
        if (keyHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("DrawTime:" + passed, 10, 400);
            System.out.println("DrawTime:" + passed);
        }
    }
    public void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(tempScreen, 0, 0, screenWidth2, screeHeight2, null);
        graphics.dispose();
    }
}
