package projectMain;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    boolean checkDrawTime = false;
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); //Like it says, returns integer keyCode associated with keyboard key pressed
        //Title State
        if (gamePanel.gameState == gamePanel.titleState) {
            titleState(keyCode);
        }
        //Player State
        if (gamePanel.gameState == gamePanel.playState) {
            playState(keyCode);
        }
        //Pause State
        else if (gamePanel.gameState == gamePanel.pauseState) {
            pauseState(keyCode);
        }
        //Dialogue State
        else if (gamePanel.gameState == gamePanel.dialogueState) {
            dialogueState(keyCode);
        }
        //Character State
        else if (gamePanel.gameState == gamePanel.characterState) {
            characterState(keyCode);
        }
        else if (gamePanel.gameState == gamePanel.optionState) {
            optionState(keyCode);
        }
    }

    public void optionState(int keyCode) {
        if (keyCode==KeyEvent.VK_ESCAPE){
            gamePanel.gameState= gamePanel.playState;
        }
        if (keyCode==KeyEvent.VK_ENTER){
            enterPressed=true;
        }
    }

    public void titleState(int keyCode){
        if (keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) { //KeyEvent: An event which indicates that a keystroke occurred in a component.
            gamePanel.ui.commandNum--;
            if (gamePanel.ui.commandNum < 0) {
                gamePanel.ui.commandNum = 2;
            }
        }
        if (keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
            gamePanel.ui.commandNum++;
            if (gamePanel.ui.commandNum > 2) {
                gamePanel.ui.commandNum = 0;
            }
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            if (gamePanel.ui.commandNum == 0) {
                gamePanel.gameState = gamePanel.playState;
            }
            if (gamePanel.ui.commandNum == 1) {
                //add later
            }
            if (gamePanel.ui.commandNum == 2) {
                System.exit(0); //Terminates the currently running Java Virtual Machine
            }
        }
    }
    public void playState(int keyCode){
        if (keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) { //KeyEvent: An event which indicates that a keystroke occurred in a component.
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_A||keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D||keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.pauseState;
        }
        if (keyCode == KeyEvent.VK_C) {
            gamePanel.gameState = gamePanel.characterState;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.optionState;
        }


        //debug
        if (keyCode == KeyEvent.VK_T) {
            if (!checkDrawTime) {
                checkDrawTime = true;
            } else if (checkDrawTime) {
                checkDrawTime = false;
            }
        }
    }
    public void pauseState(int keyCode){
        if (keyCode == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.playState;
        }
    }
    public void dialogueState(int keyCode){
        if (keyCode == KeyEvent.VK_ENTER) {
            gamePanel.gameState = gamePanel.playState;
        }
    }
    public void characterState(int keyCode){
        if (keyCode == KeyEvent.VK_C) {
            gamePanel.gameState = gamePanel.playState;
        }
        if (keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) {
            if (gamePanel.ui.slotRow !=0) {
                gamePanel.ui.slotRow--;
            }
        }
        if (keyCode == KeyEvent.VK_A||keyCode == KeyEvent.VK_LEFT) {
            if (gamePanel.ui.slotCol !=0) {
                gamePanel.ui.slotCol--;
            }
        }
        if (keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
            if (gamePanel.ui.slotRow !=3) {
                gamePanel.ui.slotRow++;
            }
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            if (gamePanel.ui.slotCol !=4) {
                gamePanel.ui.slotCol++;
            }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            gamePanel.player.selectItem();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) { //KeyEvent: An event which indicates that a keystroke occurred in a component.
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_A||keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}