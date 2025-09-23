package Monster;

import entity.Entity;
import object.Objctect_GoldenCoin;
import object.Object_Heart;
import object.Object_ManaCrystals;
import object.Object_Rock;
import projectMain.GamePanel;

import java.awt.*;
import java.util.Random;

public class BigSnake extends Entity {
    GamePanel gamePanel;
    public BigSnake(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Big Snake";
        speed = 3;
        maxLife = 20;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp=2;
        projectile=new Object_Rock(gamePanel);

        solidArea = new Rectangle(3, 16, 42, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadMonsterImage();
    }

    public void loadMonsterImage() {
        up1 = setUp("/Monster/bigsnake_Monster-3", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setUp("/Monster/bigsnake_Monster-4", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setUp("/Monster/bigsnake_Monster-1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setUp("/Monster/bigsnake_Monster-1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setUp("/Monster/bigsnake_Monster-1_2", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setUp("/Monster/bigsnake_Monster-1_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setUp("/Monster/bigsnake_Monster-5", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setUp("/Monster/bigsnake_Monster-6", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100)+1;
        if (i>99&&!projectile.alive&&shotAvailableCounter==30){//make mosnters randomly shoot
            projectile.set(worldX,worldY,direction,true,this);
            gamePanel.projectileList.add(projectile);
            shotAvailableCounter=0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gamePanel.player.direction; //move away from player
    }

    @Override
    public void checkDrop() {
        //cast a die
         int i = new Random().nextInt(100) + 1;
         //set monster drop
        if (i < 50){
            dropItem(new Objctect_GoldenCoin(gamePanel));
        }
        if (i >= 50 && i < 75){
            dropItem(new Object_Heart(gamePanel));
        }
        if (i >= 75 && i < 100){
            dropItem(new Object_ManaCrystals(gamePanel));
        }
    }
}
