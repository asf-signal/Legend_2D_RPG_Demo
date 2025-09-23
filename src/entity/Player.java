package entity;

import object.*;
import projectMain.GamePanel;
import projectMain.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyBoardUse;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public int maxInventorySize = 20;

    public Player(GamePanel gamePanel, KeyHandler keyBoardUse) {
        super(gamePanel);
        this.keyBoardUse = keyBoardUse;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screeHeight / 2 - (gamePanel.tileSize / 2);
        //solid are
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        //attack area
        /*attackArea.width = 36;
        attackArea.height = 36;*/

        setValues();
        loadPlayerCharacterImage();
        loadPlayerAttackImage();
        setItems();
    }

    public void setValues() {
        worldX = gamePanel.tileSize * 3;
        worldY = gamePanel.tileSize * 3;
        speed = 4;
        direction = "down";
        //player status
        level = 1;
        maxLife = 7;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo=10;
        strength = 1; //the more strength acquired the more damage given
        dexterity = 1; //the more dexterity, the less damage received
        exp = 0;
        nextLevelXP = 5;
        coin = 0;
        currentWeapon = new Object_Sword_Normal(gamePanel);
        //currentWeapon = new Object_Ax_Normal(gamePanel);
        currentShield = new Object_Shield_Wood(gamePanel);
        projectile = new Object_Watersplash(gamePanel);
        //projectile = new Object_Rock(gamePanel);
        attack = getAttack();//total attack value decided by strength and weapon
        defense = getDefense();//total defense value decided by dexterity and shield
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Object_Key(gamePanel));
    }

    private int getDefense() {
        return defense = dexterity * currentWeapon.defenseValue;
    }

    private int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public void loadPlayerCharacterImage() {
            up1 = setUp("/playerTiles/Loyd-04", gamePanel.tileSize, gamePanel.tileSize);
            up1 = setUp("/playerTiles/Loyd-05", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setUp("/playerTiles/Loyd-06", gamePanel.tileSize, gamePanel.tileSize);
            left2 = setUp("/playerTiles/Loyd-07", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setUp("/playerTiles/Loyd-02", gamePanel.tileSize, gamePanel.tileSize);
            right2 = setUp("/playerTiles/Loyd-03", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setUp("/playerTiles/Loyd-00", gamePanel.tileSize, gamePanel.tileSize);
            down2 = setUp("/playerTiles/Loyd-01", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void loadPlayerAttackImage() {
        if(currentWeapon.type==type_sword) {
            //SWORD FIGHT IMAGES
            fightD1 = setUp("/playerTiles/Loyd_Fight_d1", gamePanel.tileSize, gamePanel.tileSize);
            fightD2 = setUp("/playerTiles/Loyd_Fight_d2", gamePanel.tileSize, gamePanel.tileSize);
            fightU1 = setUp("/playerTiles/Loyd_Fight_u1", gamePanel.tileSize, gamePanel.tileSize);
            fightU2 = setUp("/playerTiles/Loyd_Fight_u2", gamePanel.tileSize, gamePanel.tileSize);
            fightR1 = setUp("/playerTiles/Loyd_Fight_r1", gamePanel.tileSize, gamePanel.tileSize);
            fightR2 = setUp("/playerTiles/Loyd_Fight_r2", gamePanel.tileSize, gamePanel.tileSize);
            fightL1 = setUp("/playerTiles/Loyd_Fight_l1", gamePanel.tileSize, gamePanel.tileSize);
            fightL2 = setUp("/playerTiles/Loyd_Fight_l2", gamePanel.tileSize, gamePanel.tileSize);
        } else if (currentWeapon.type==type_ax) { //PRETEND WE HAVE AN AX INSTEAD OF SWORD I
            //HAVEN'T FINISHED THE SPRITES!!
            fightD1 = setUp("/playerTiles/Loyd_Fight_d1", gamePanel.tileSize, gamePanel.tileSize);
            fightD2 = setUp("/playerTiles/Loyd_Fight_d2", gamePanel.tileSize, gamePanel.tileSize);
            fightU1 = setUp("/playerTiles/Loyd_Fight_u1", gamePanel.tileSize, gamePanel.tileSize);
            fightU2 = setUp("/playerTiles/Loyd_Fight_u2", gamePanel.tileSize, gamePanel.tileSize);
            fightR1 = setUp("/playerTiles/Loyd_Fight_r1", gamePanel.tileSize, gamePanel.tileSize);
            fightR2 = setUp("/playerTiles/Loyd_Fight_r2", gamePanel.tileSize, gamePanel.tileSize);
            fightL1 = setUp("/playerTiles/Loyd_Fight_l1", gamePanel.tileSize, gamePanel.tileSize);
            fightL2 = setUp("/playerTiles/Loyd_Fight_l2", gamePanel.tileSize, gamePanel.tileSize);
        }
    }

    public void update() {
        if (attacking) {
            attacking();
        } else if (keyBoardUse.upPressed || keyBoardUse.downPressed ||
                keyBoardUse.leftPressed || keyBoardUse.rightPressed || keyBoardUse.enterPressed) {
            if (keyBoardUse.upPressed) {
                direction = "up";
            } else if (keyBoardUse.downPressed) {
                direction = "down";
            } else if (keyBoardUse.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionDetector.checkTile(this);

            //check obj collision
            int objIndex = gamePanel.collisionDetector.checkObject(this, true);
            pickUpObject(objIndex);

            //check npc collision
            int npcIndex = gamePanel.collisionDetector.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            //check monster collision
            int monsterIndex = gamePanel.collisionDetector.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);
            //check interactive tile collision
            int iTileIndex = gamePanel.collisionDetector.checkEntity(this, gamePanel.interactiveTile);
            //CHECK EVENT
            gamePanel.eventHandler.checkEvent();

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyBoardUse.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
            if (keyBoardUse.enterPressed && !attackCanceled) {
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;

            gamePanel.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
                standCounter++;
            if (standCounter == 20) {
                spriteNum=1;
                standCounter=0;
            }
        }
        if (gamePanel.keyHandler.shotKeyPressed&&!projectile.alive&&shotAvailableCounter==30&& projectile.haveResources(this)) {
            //set default coordinates, direction, and user
            projectile.set(worldX,worldY,direction,true,this);
            //substract cost
            projectile.substractResource(this);
            //add it to list
            gamePanel.projectileList.add(projectile);
            shotAvailableCounter=0;
        }
            //outside of key if statement!
            if (invisible) {
                invisibleCounter++;
                if (invisibleCounter > 60) {
                    invisible = false;
                    invisibleCounter = 0;
                }
            }
            if(shotAvailableCounter<30){
                shotAvailableCounter++;
            }
        }
        private void attacking() {
            spriteCounter++;
            if (spriteCounter <= 5) {
                spriteNum = 1;
            }
            if (spriteCounter > 5 && spriteCounter <= 25) {
                spriteNum = 2;
                //save cWX & cWH & sA
                int currentWorldX = worldX;
                int currentWorldY = worldY;
                int solidAreaWidth = solidArea.width;
                int solidAreaHeight = solidArea.height;
                //adjust WX/Yfor attackArea
                switch (direction) {
                    case "up": worldY -= attackArea.height;break;
                    case "down": worldY += attackArea.height;break;
                    case "left": worldX -= attackArea.width;break;
                    case "right": worldX += attackArea.width;break;
                }
                solidArea.width = attackArea.width;
                solidArea.height = attackArea.height;
                //check monster coallision
                int monsterIndex = gamePanel.collisionDetector.checkEntity(this, gamePanel.monster);
                damageMonster(monsterIndex, attack);
                worldX = currentWorldX;
                worldY = currentWorldY;
                solidArea.width = solidAreaWidth;
                solidArea.height = solidAreaHeight;
            }
            int iTileIndex = gamePanel.collisionDetector.checkEntity(this, gamePanel.interactiveTile);
            damageITile(iTileIndex);
            if (spriteCounter > 25) {
                spriteNum = 1;
                spriteCounter = 0;
                attacking = false;
            }

        }
    public void pickUpObject(int i) {

            if (i != 999) {
                //pick up only items
                if (gamePanel.obj[i].type == type_pickUpOnly) {
                    gamePanel.obj[i].use(this);
                    gamePanel.obj[i]=null;
                }
                //inventory items
                else {
                    String text = "";
                    String objectName = gamePanel.obj[i].name;
                   /* switch (objectName) {
                        case "Key":
                            hasKey++;
                            if (inventory.size() != maxInventorySize) {
                                inventory.add(gamePanel.obj[i]);
                                text = "Got a " + gamePanel.obj[i].name + "!";
                            } else {
                                text = "Inventory FULL!";
                            }
                            gamePanel.ui.addMessage(text);
                            gamePanel.obj[i] = null;
                            break;
                        case "Door":
                            if (hasKey == 0) {
                                //System.out.println("NEED KEY!");
                                gamePanel.ui.addMessage("U NEED KEY!!");

                            } else if (hasKey > 0) {
                                gamePanel.obj[i] = null;
                                hasKey--;
                                //System.out.println("Key:" + hasKey + " + Door open");
                                gamePanel.ui.addMessage("U Opened Door!");
                            }
                            // System.out.println("Key:" + hasKey);
                            break;
                        case "Treasure":
                            if (hasKey == 0) {
                                gamePanel.ui.addMessage("U NEED KEY!!");
                            } else if (hasKey > 0) {
                                gamePanel.obj[i] = null;
                                hasKey--;
                                // System.out.println("Key:" + hasKey + " + Chest open");
                                gamePanel.ui.addMessage("U Opened Chest!");

                            }
                            System.out.println("Key:" + hasKey);
                            break;
                        case "Bed":
                            gamePanel.ui.addMessage("Not bed time yet!");
                            break;
                        case "Woodcutter's Ax":
                            if (inventory.size() != maxInventorySize) {
                                inventory.add(gamePanel.obj[i]);
                                text = "Got a " + gamePanel.obj[i].name + "!";
                            } else {
                                text = "Inventory FULL!";
                            }
                            gamePanel.ui.addMessage(text);
                            gamePanel.obj[i] = null;
                            break;
                        case "SkaterGirl Themed Wooden Shield" :
                            if (inventory.size() != maxInventorySize) {
                                inventory.add(gamePanel.obj[i]);
                                text = "Got a " + gamePanel.obj[i].name + "!";
                            } else {
                                text = "Inventory FULL!";
                            }
                            gamePanel.ui.addMessage(text);
                            gamePanel.obj[i] = null;
                            break;
                        case "Wooden Shield" :
                            if (inventory.size() != maxInventorySize) {
                                inventory.add(gamePanel.obj[i]);
                                text = "Got a " + gamePanel.obj[i].name + "!";
                            } else {
                                text = "Inventory FULL!";
                            }
                            gamePanel.ui.addMessage(text);
                            gamePanel.obj[i] = null;
                            break;
                        case "red potion" :
                            if (inventory.size() != maxInventorySize) {
                                inventory.add(gamePanel.obj[i]);
                                text = "Got a " + gamePanel.obj[i].name + "!";
                            } else {
                                text = "Inventory FULL!";
                            }
                            gamePanel.ui.addMessage(text);
                            gamePanel.obj[i] = null;
                            break;
                        case "Sword" :
                            if (inventory.size() != maxInventorySize) {
                                inventory.add(gamePanel.obj[i]);
                                text = "Got a " + gamePanel.obj[i].name + "!";
                            } else {
                                text = "Inventory FULL!";
                            }
                            gamePanel.ui.addMessage(text);
                            gamePanel.obj[i] = null;
                            break;

                    }*/
                    if (inventory.size() != maxInventorySize) {
                        inventory.add(gamePanel.obj[i]);
                        text = "Got a " + gamePanel.obj[i].name + "!";
                    }
                    else {
                        text = "You cannot carry anymore items!";
                    }
                gamePanel.ui.addMessage(text);
                gamePanel.obj[i] = null;
                }
            }
        }

        public void interactNPC(int i) {
            if (gamePanel.keyHandler.enterPressed) {
                if (i != 999) {
                    attackCanceled = true;
                    //System.out.println("U r hitting me!");
                    gamePanel.gameState = gamePanel.dialogueState;
                    gamePanel.npc[i].speak();
                }
            }
            //gamePanel.keyHandler.enterPressed = false;
        }
        public void contactMonster(int i) {
            if (i != 999) {
                if (!invisible&&!gamePanel.monster[i].dying) {
                    int damage = gamePanel.monster[i].attack - defense;
                    if (damage < 0) {
                        damage = 0;
                    }
                    life -= damage;
                    invisible = true;
                }
            }
        }

        public void damageMonster(int i, int attack) {
            if (i != 999) {
                if (!gamePanel.monster[i].invisible) {
                    int damage = attack - gamePanel.monster[i].defense;
                    if (damage < 0) {
                        damage = 0;
                    }
                    gamePanel.monster[i].life -= damage;
                    gamePanel.ui.addMessage(damage + " damage!");

                    gamePanel.monster[i].invisible = true;
                    gamePanel.monster[i].damageReaction();
                    if (gamePanel.monster[i].life <= 0) {
                        gamePanel.monster[i].dying = true;
                        gamePanel.ui.addMessage("Defeated the " + gamePanel.monster[i].name + "!");
                        gamePanel.ui.addMessage("Exp + " + gamePanel.monster[i].exp + "!");
                        exp += gamePanel.monster[i].exp;
                        checkLevelUp();
                    }
                }
            }
        }
        public void damageITile(int i) {
            if (i != 999 && gamePanel.interactiveTile[i].destructible &&
                    gamePanel.interactiveTile[i].isCorrectItem(this) &&
                    !gamePanel.interactiveTile[i].invisible) {
                /*if (!gamePanel.interactiveTile[i].invisible) {
                    int damage = attack - gamePanel.interactiveTile[i].defense;
                    if (damage < 0) {
                        damage = 0;
                    }
                    gamePanel.interactiveTile[i].life -= damage;
                    gamePanel.ui.addMessage(damage + " damage!");

                    gamePanel.interactiveTile[i].invisible = true;
                    gamePanel.interactiveTile[i].damageReaction();
                    if (gamePanel.interactiveTile[i].life <= 0) {
                        gamePanel.interactiveTile[i].dying = true;
                        gamePanel.ui.addMessage("Defeated the " + gamePanel.interactiveTile[i].name + "!");
                        gamePanel.ui.addMessage("Exp + " + gamePanel.interactiveTile[i].exp + "!");
                        exp += gamePanel.interactiveTile [i].exp;
                        checkLevelUp();
                    }
                }*/
                gamePanel.interactiveTile[i].life--;
                gamePanel.interactiveTile[i].invisible = true;
                //generate particle
                generateParticle(gamePanel.interactiveTile[i], gamePanel.interactiveTile[i]);
                if(gamePanel.interactiveTile[i].life==0){
                    gamePanel.interactiveTile[i]= gamePanel.interactiveTile[i].getDestroyedForm();
                }
            }
        }

        public void checkLevelUp() {
            if (exp >= nextLevelXP) {
                level++;
                nextLevelXP *= nextLevelXP;
                maxLife += 2;
                strength++;
                dexterity++;
                attack = getAttack();
                defense = getDefense();

                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.ui.currentDialogue = "Leveled Up!" + level + "You feel Stronger!";
            }
        }

        public void selectItem() {
            int itemIndex = gamePanel.ui.getItemIndexonSlot();
            if(itemIndex < inventory.size()){
                Entity selectedItem = inventory.get(itemIndex);

                if (selectedItem.type == type_sword || selectedItem.type == type_ax) {
                    currentWeapon=selectedItem;
                    attack = getAttack();
                    loadPlayerAttackImage();
                } else if (selectedItem.type == type_shield) {
                    currentShield = selectedItem;
                    defense=getDefense();
                } else if (selectedItem.type == type_consumable) {
                    selectedItem.use(this);
                    inventory.remove(itemIndex);
                }
            }
        }
        public void draw(Graphics2D graphics2D) {
    //        graphics2D.setColor(Color.WHITE);
    //        graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
            BufferedImage image = null;
            int tempScreenX = screenX;
            int tempScreenY = screenY;
            switch (direction) {
                case "up":
                    if (!attacking) {
                        if (spriteNum == 1) {
                            image = up1;
                        }
                        if (spriteNum == 2) {
                            image = up2;
                        }
                    }
                    if (attacking) {
                        //  tempScreenX = screenX - gamePanel.tileSize;
                        if (spriteNum == 1) {
                            image = fightU1;
                        }
                        if (spriteNum == 2) {
                            image = fightU2;
                        }
                    }
                    break;
                case "down":
                    if (!attacking) {
                        if (spriteNum == 1) {
                            image = down1;
                        }
                        if (spriteNum == 2) {
                            image = down2;
                        }
                    }
                    if (attacking) {
                        // tempScreenX = screenX - gamePanel.tileSize;
                        if (spriteNum == 1) {
                            image = fightD1;
                        }
                        if (spriteNum == 2) {
                            image = fightD2;
                        }
                    }
                    break;
                case "left":
                    if (!attacking) {
                        if (spriteNum == 1) {
                            image = left1;
                        }
                        if (spriteNum == 2) {
                            image = left2;
                        }
                    }
                    if (attacking) {
                        //tempScreenX = screenX - gamePanel.tileSize;
                        if (spriteNum == 1) {
                            image = fightL1;
                        }
                        if (spriteNum == 2) {
                            image = fightL2;
                        }
                    }
                    break;
                case "right":
                    if (!attacking) {
                        if (spriteNum == 1) {
                            image = right1;
                        }
                        if (spriteNum == 2) {
                            image = right2;
                        }
                    }
                    if (attacking) {
                        // tempScreenX = screenX - gamePanel.tileSize;
                        if (spriteNum == 1) {
                            image = fightR1;
                        }
                        if (spriteNum == 2) {
                            image = fightR2;
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }

            if (invisible) {
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f));
            }
            graphics2D.drawImage(image, tempScreenX, tempScreenY, null);
            //rest alpha
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //debug
    //        graphics2D.setFont(new Font("Arial", Font.PLAIN, 26));
    //        graphics2D.setColor(Color.WHITE);
    //        graphics2D.drawString("invisible " + invisibleCounter, 10, 400);
        }
    }
