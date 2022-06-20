package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, attack;
	GamePanel gp;
	
	public boolean Askill1IsActive = false;
	public boolean Askill2IsActive = false;
	public boolean Askill3IsActive = false;
	
	public KeyHandler(){
		// TODO Auto-generated constructor stub
	}

	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		int code = e.getKeyCode();
		
//		TITLE SCREEN
		if (gp.gameState == gp.titleState) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
			}	
			
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
				}
				else if (gp.ui.commandNum == 1) {
					gp.gameState = gp.guideState;
				}
				else if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}
		
//		GUIDE SCREEN
		if (gp.gameState == gp.guideState) {
			if (code == KeyEvent.VK_B) {
				gp.gameState = gp.titleState;
			}
		}
		
//		GAME STATE
		if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_P) {
				gp.isPause = !gp.isPause;
			}
			if (!gp.isPause) {
				if (code == KeyEvent.VK_W) {
					System.out.println("D");
					upPressed = true;
				}
				if (code == KeyEvent.VK_S) {
					System.out.println("S");
					downPressed = true;
				}
				if (code == KeyEvent.VK_A) {
					System.out.println("A");
					leftPressed = true;
				}
				if (code == KeyEvent.VK_D) {
					System.out.println("D");
					rightPressed = true;
				}

				if (code == KeyEvent.VK_F) {
					attack = true;
				}
				if (code == KeyEvent.VK_1) {
					Askill1IsActive = true;
				}
				if (code == KeyEvent.VK_2) {
					Askill2IsActive = true;
				}
				if (code == KeyEvent.VK_3) {
					Askill3IsActive = true;
				}	
			}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			attack = false;
		}
		if (code == KeyEvent.VK_1) {
			Askill1IsActive = false;
		}
		if (code == KeyEvent.VK_2) {
			Askill2IsActive = false;
		}
		if (code == KeyEvent.VK_3) {
			Askill3IsActive = false;
		}
		
	}

}
