package entity;

import projectMain.GamePanel;
import projectMain.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    GamePanel gamePanel;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage fightU1, fightU2, fightD1, fightD2, fightL1, fightL2, fightR1, fightR2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean invisible = false;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    //character status

    public BufferedImage image, image2, image3;

    //state
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean collision = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    //counter
    public int spriteCounter = 0;
    public int invisibleCounter = 0;
    public int actionLockCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    //character attributes
    public String name;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int speed;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelXP;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //item attribute
    public  int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    //type
    public int type; //0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_ax = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickUpOnly = 7;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public BufferedImage setUp(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            scaledImage = utilityTool.scaleImage(scaledImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void setAction() {
    }

    public void damageReaction() {
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction) {
            case "up": direction = "down";break;
            case "down": direction = "up";break;
            case "left": direction = "right";break;
            case "right": direction = "left";break;
        }
    }
    public void use(Entity entity){}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gamePanel.obj.length; i++) {
            if(gamePanel.obj[i] == null) {
                gamePanel.obj[i] = droppedItem;
                gamePanel.obj[i].worldX = worldX;//dead mosters place(worldX
                gamePanel.obj[i].worldY = worldY;
                break;
            }
        }
    }
    public Color getParticleColor (){
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxlife = generator.getParticleMaxLife();

        Particle particle = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle particle2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle particle3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle particle4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2,  1);
        gamePanel.particleList.add(particle);
        gamePanel.particleList.add(particle2);
        gamePanel.particleList.add(particle3);
        gamePanel.particleList.add(particle4);
    }

    public void update() {
        setAction();
        collisionOn = false;
        gamePanel.collisionDetector.checkTile(this);
        gamePanel.collisionDetector.checkObject(this, false);
        gamePanel.collisionDetector.checkEntity(this, gamePanel.npc);
        gamePanel.collisionDetector.checkEntity(this, gamePanel.monster);
        gamePanel.collisionDetector.checkEntity(this, gamePanel.interactiveTile );
        boolean contactPlayer = gamePanel.collisionDetector.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }

        //IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invisible) {
            invisibleCounter++;
            if (invisibleCounter > 40) {
                invisible = false;
                invisibleCounter = 0;
            }
        }
        if (shotAvailableCounter<30) {
            shotAvailableCounter++;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void damagePlayer(int attack){
        if (!gamePanel.player.invisible) {
            //give damage
            int damage = attack - gamePanel.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gamePanel.player.life -= damage;
            gamePanel.player.invisible = true;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
            //monster hp bar
            if (type == 2 && hpBarOn) {
                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarVal = oneScale * life;

                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize, 12);

                graphics2D.setColor(new Color(225, 0, 30));
                graphics2D.fillRect(screenX, screenY - 15, (int) hpBarVal, 10);
                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if (invisible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(graphics2D, .4f);
            }
            if (dying) {
                dyingAnimation(graphics2D);
            }
            graphics2D.drawImage(image, screenX, screenY, null);
            changeAlpha(graphics2D, 1F);
        }
    }

    public void dyingAnimation(Graphics2D graphics2D) {
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= i) {
            changeAlpha(graphics2D, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(graphics2D, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {changeAlpha(graphics2D, 0f);}
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {changeAlpha(graphics2D, 0f);}
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D graphics2D, float alphaValue) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }
}