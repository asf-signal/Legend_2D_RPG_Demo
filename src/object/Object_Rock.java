package object;

import entity.Entity;
import entity.Projectile;
import projectMain.GamePanel;

import java.awt.*;

public class Object_Rock extends Projectile {
    GamePanel gamePanel;
    public Object_Rock(GamePanel gamePanel){
        super(gamePanel);
        this.gamePanel=gamePanel;
        name="Rock";
        speed=8;
        maxLife=80;
        life =maxLife;
        attack=2;
        useCost=1;
        alive=false;
        getImage();
    }

    public void getImage() {
        up1=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        up1=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        down1=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        down2=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        left1=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        left2=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        right1=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
        right2=setUp("/projectiles/rock",gamePanel.tileSize,gamePanel.tileSize);
    }
    public boolean haveResources(Entity user) {
        boolean haveResource = user.ammo >= useCost;
        return haveResource;
    }
    public void substractResource(Entity user) {
        user.ammo-=useCost;
    }
    public Color getParticleColor (){
        Color color = new Color(182, 104, 4);
        return color;
    }
    public int getParticleSize() {
        int size = 6;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
