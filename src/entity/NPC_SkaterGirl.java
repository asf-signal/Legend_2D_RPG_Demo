package entity;

import projectMain.GamePanel;

import java.util.Random;

public class NPC_SkaterGirl extends Entity {
    public NPC_SkaterGirl(GamePanel gamePanel) {
        super(gamePanel);

        direction = "right";
        speed = 4;

        loadNPCImage();
        loadDialogue();
    }

    public void loadNPCImage() {
        up1 = setUp("/NPC/skaterGirl_up", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setUp("/NPC/skaterGirl_up2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setUp("/NPC/skaterGirl_l", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setUp("/NPC/skaterGirl_l2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setUp("/NPC/skaterGirl_r", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setUp("/NPC/skaterGirl_r2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setUp("/NPC/skaterGirl_d", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setUp("/NPC/skaterGirl_d2", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void loadDialogue() {
        dialogues[0] = "Hey! I haven't seen ya around \nYou new here?";
        dialogues[1] = "Are you Legend??!! It must being tough being the\nchosen to save us at such a young age.";
        dialogues[2] = "In a few minutes there will be a \nA VERY SCARY MONSTER \n Will you fight it?";
        dialogues[3] = "If you do I'll reward you with some food. \n You don't wanna miss out on some awesome \n Spicy Chicken! It helps boost your health!";
    }

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
    }
    public void speak() {
        //do character specific stuff
        super.speak();
    }
}
