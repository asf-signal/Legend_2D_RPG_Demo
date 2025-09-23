package projectMain;

import entity.Entity;
import object.Object_Heart;
import object.Object_Key;
import object.Object_ManaCrystals;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.util.ArrayList;

public class UI {
    GamePanel gamePanel;
    BufferedImage heart1, heart2, heart3,crystal_full,crystal_blank;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("arial", Font.PLAIN, 40);
        arial_80B = new Font("arial", Font.BOLD, 80);

        //create HUD objects
        Entity heart = new Object_Heart(gamePanel);
        heart1 = heart.image;
        heart2 = heart.image2;
        heart3 = heart.image3;
        Entity crystal = new Object_ManaCrystals(gamePanel);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2; 
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.WHITE);
        //title state
        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen(graphics2D);
        }
        //play state
        if (gamePanel.gameState == gamePanel.playState) {
            //do play stuff later
            drawPlayerLife(graphics2D);
            drawMessage(graphics2D);
        }
        //pause state
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife(graphics2D);
            drawPause(graphics2D);
        }
        //dialogue state
        if (gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerLife(graphics2D);
            drawDialogueScreen(graphics2D);
        }
        //character state
        if (gamePanel.gameState == gamePanel.characterState) {
            drawCharacterScreen(graphics2D);
            drawInventory(graphics2D);
        }
        if (gamePanel.gameState == gamePanel.characterState) {
            drawOptionsScreen(graphics2D);
        }
    }

    private void drawOptionsScreen(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        //sub window
        int frameX = gamePanel.tileSize*6;
        int frameY=gamePanel.tileSize;
        int frameWidth=gamePanel.tileSize*8;
        int frameHeight =gamePanel.tileSize*10;
        drawSubWindow(graphics2D, frameX, frameY, frameWidth, frameHeight);
    }

    private void drawInventory(Graphics2D graphics2D) {
        int frameX = gamePanel.tileSize * 12;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;
        drawSubWindow(graphics2D, frameX, frameY, frameWidth, frameHeight);

        //slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gamePanel.tileSize+3;

        //Draw Player's items
        for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
            //equip cursor
            if (gamePanel.player.inventory.get(i) == gamePanel.player.currentWeapon ||
                    gamePanel.player.inventory.get(i) == gamePanel.player.currentShield) {
                graphics2D.setColor(new Color(184, 122, 245));
                graphics2D.fillRoundRect(slotX, slotY,gamePanel.tileSize,gamePanel.tileSize,10,10);
            }

            graphics2D.drawImage(gamePanel.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;
        //draw cursor
        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        //description frame
        int dframeX=frameX;
        int dframeY=frameY+frameHeight;
        int dframeWidth=frameWidth;
        int dframeXHeight=gamePanel.tileSize*3;
        //draw description text
        int textX=dframeX+20;
        int textY=dframeY+gamePanel.tileSize;
        graphics2D.setFont(graphics2D.getFont().deriveFont(28F));

        int itemIndex = getItemIndexonSlot();
        if (itemIndex <gamePanel.player.inventory.size()) {
            drawSubWindow(graphics2D,dframeX, dframeY, dframeWidth, dframeXHeight);
            for (String line: gamePanel.player.inventory.get(itemIndex).description.split("\n")) {
                graphics2D.drawString(line, textX, textY);
                textY+=32;
            }
        }
    }
    public int getItemIndexonSlot(){
        int itemIndex = slotCol+(slotRow*5);
        return itemIndex;
    }
    public void drawMessage(Graphics2D graphics2D) {
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                graphics2D.setColor(Color.black);
                graphics2D.drawString(message.get(i), messageX + 2, messageY + 2);
                graphics2D.setColor(Color.white);
                graphics2D.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;//messageCounter++
                messageCounter.set(i, counter);//setCounter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPlayerLife(Graphics2D graphics2D) {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        //draw maxlife
        while (i < gamePanel.player.maxLife / 2) {
            graphics2D.drawImage(heart3, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        //reset
        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        //draw current heart
        while (i < gamePanel.player.life) {
            graphics2D.drawImage(heart2, x, y, null);
            i++;
            if (i < gamePanel.player.life) {
                graphics2D.drawImage(heart1, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }
        //draw max mana
        x=(gamePanel.tileSize/2)-5;
        y=(int) (gamePanel.tileSize*1.5);
        i=0;
        while (i<gamePanel.player.maxMana){
            graphics2D.drawImage(crystal_blank, x, y, null);
            i++;
            x+=35;
        }
        //draw mana
        x=(gamePanel.tileSize/2)-5;
        y=(int) (gamePanel.tileSize*1.5);
        i=0;
        while (i<gamePanel.player.mana){
            graphics2D.drawImage(crystal_full, x, y, null);
            i++;
            x+=35;
        }
    }

    public void drawTitleScreen(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screeHeight);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 70F));
        String text = "Legend's Adventure";
        int x = getXForCenteredText(graphics2D, text);
        int y = gamePanel.tileSize * 3;

        //shadow
        graphics2D.setColor(Color.gray);
        graphics2D.drawString(text, x + 5, y + 5);

        //main color
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);

        //player image
        x = gamePanel.screenWidth / 2 - gamePanel.tileSize * 2;
        y += gamePanel.tileSize * 2;
        graphics2D.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 8, gamePanel.tileSize * 8, null);

        //menu
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 35F));

        text = "NEW GAME";
        x = getXForCenteredText(graphics2D, text) - 200;
        y += gamePanel.tileSize * .05;
        graphics2D.drawString(text, x, y);
        if (commandNum == 0) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(graphics2D, text) - 200;
        y += gamePanel.tileSize * .8;
        graphics2D.drawString(text, x, y);
        if (commandNum == 1) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "QUIT GAME";
        x = getXForCenteredText(graphics2D, text) - 200;
        y += gamePanel.tileSize * .8;
        graphics2D.drawString(text, x, y);
        if (commandNum == 2) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }
    }

    public void drawPause(Graphics2D graphics2D) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(graphics2D, text);
        int y = gamePanel.screeHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen(Graphics2D graphics2D) {
        //window
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;
        drawSubWindow(graphics2D, x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 22F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
        //graphics2D.drawString(currentDialogue, x, y);
    }

    public void drawCharacterScreen(Graphics2D graphics2D) {
        //create a frame
        final int frameX = gamePanel.tileSize*2;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(graphics2D, frameX, frameY, frameWidth, frameHeight);

        //text
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        //names
        graphics2D.drawString("Level", textX, textY);textY += lineHeight;
        graphics2D.drawString("Life", textX, textY);textY += lineHeight;
        graphics2D.drawString("Mana", textX, textY);textY += lineHeight;
        graphics2D.drawString("Strength", textX, textY);textY += lineHeight;
        graphics2D.drawString("Dexterity", textX, textY);textY += lineHeight;
        graphics2D.drawString("Attack", textX, textY);textY += lineHeight;
        graphics2D.drawString("Defense", textX, textY);textY += lineHeight;
        graphics2D.drawString("Exp", textX, textY);textY += lineHeight;
        graphics2D.drawString("Next Level", textX, textY);textY += lineHeight;
        graphics2D.drawString("Coin", textX, textY);textY += lineHeight + 10;
        graphics2D.drawString("Weapon", textX, textY);textY += lineHeight + 15;
        graphics2D.drawString("Shield", textX, textY);textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;
        //reset textY
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.life + "/" + gamePanel.player.maxLife;
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.mana + "/" + gamePanel.player.maxMana;
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelXP);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXForAlignToRightText(graphics2D, value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        graphics2D.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;
        graphics2D.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;

    }

    public void drawSubWindow(Graphics2D graphics2D, int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        graphics2D.setColor(color);
        //graphics2D.setColor(Color.BLACK);
        graphics2D.fillRoundRect(x, y, width, height, 25, 25);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 15, 15);
    }

    public int getXForCenteredText(Graphics2D graphics2D, String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXForAlignToRightText(Graphics2D graphics2D, String text, int tailX) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = tailX - length;
        return x;
    }
}
