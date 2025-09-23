package entity;

import projectMain.GamePanel;

import java.awt.*;

public class Particle extends Entity {
    GamePanel gamePanel;

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gp, Entity g, Color c, int s, int sp, int maxLife, int xd, int yd) {
        super(gp);
        gamePanel = gp;
        generator = g;
        color = c;
        this.size = s;
        this.speed = sp;
        this.xd = xd;
        this.yd = yd;
        life = maxLife;
        int offset = (gamePanel.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    @Override
    public void update() {
        super.update();
        life--;
        if (life<maxLife/3){
            yd++;
        }
        worldX += xd * speed;
        worldY += yd * speed;

        if (life == 0) {
            alive = false;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }
}
