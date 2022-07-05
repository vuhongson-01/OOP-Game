package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import screen.LevelOptionScreen;
import screen.LoadingScreen;
import screen.MoreScreen;
import screen.OptionScreen;
import screen.PlayerOptionScreen;
import screen.SettingScreen;
import screen.StartLauncher;

public class DemoMain extends JFrame implements GameInterface{

    CardLayout cardLayout = new CardLayout();;
    JPanel mainPanel;
    StartLauncher startLauncher;
    Thread gameThread;
    Stack<ScreenComponent> stackOfScreen = new Stack<ScreenComponent>();
    ScreenComponent screen;
    ScreenComponent startLauncherScreen,
    				optionScreen,
    				settingScreen,
    				moreScreen,
    				playerOptionScreen,
    				levelOptionScreen;
    GamePanel gamePanel;
    LoadingScreen loadingScreen = new LoadingScreen();
    
    public int level = 1;
    
    public DemoMain(){
        initScreen();
        gameThread.start();
        pushStartLauncherScreen();
        
        setResizable(false);
        mainPanel.setFocusable(true);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
        
//        setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, getInputMap());
//        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
//        getInputMap().put(key, "pressed");    
        
//        mainPanel.requestFocusInWindow();
        
        cardLayout.show(mainPanel, "loadingScreen");
        Timer timer = new Timer(1000, new ActionListener(){
        	  @Override
        	  public void actionPerformed( ActionEvent e ){
        		  cardLayout.show(mainPanel, startLauncherScreen.screenName);
        	  }
        	} );
        timer.setRepeats( false );
        timer.start();
    }
    
    
    void initScreen() {
    	mainPanel = new JPanel(cardLayout);
    	gameThread = new Thread();
    	
    	startLauncherScreen = new ScreenComponent(new StartLauncher(this), "startLauncher");
        optionScreen = new ScreenComponent(new OptionScreen(this), "optionScreen");
        settingScreen = new ScreenComponent(new SettingScreen(this), "settingScreen");
        moreScreen = new ScreenComponent(new MoreScreen(this), "moreScreen");
        playerOptionScreen = new ScreenComponent(new PlayerOptionScreen(this), "playerOptionScreen");
        levelOptionScreen = new ScreenComponent(new LevelOptionScreen(this), "levelOptionScreen");
        
        mainPanel.add(loadingScreen, "loadingScreen");
        mainPanel.add(startLauncherScreen.screen, startLauncherScreen.screenName);
        mainPanel.add(optionScreen.screen, optionScreen.screenName);
        mainPanel.add(settingScreen.screen, settingScreen.screenName);
        mainPanel.add(moreScreen.screen, moreScreen.screenName);
        mainPanel.add(playerOptionScreen.screen, playerOptionScreen.screenName);
        mainPanel.add(levelOptionScreen.screen, levelOptionScreen.screenName);
    }
    
    public void pushStartLauncherScreen() {
    	stackOfScreen.push(startLauncherScreen);
    }
    
    public void pushOptionScreen() {
    	stackOfScreen.push(optionScreen);
    }
    
    public void pushSettingScreen() {
    	stackOfScreen.push(settingScreen);
    }

    public void pushMoreScreen() {
    	stackOfScreen.push(moreScreen);
    }
    
    public void pushPlayerOptionScreen() {
    	stackOfScreen.push(playerOptionScreen);
    }
    
    public void pushLevelOptionScreen() {
    	stackOfScreen.push(levelOptionScreen);
    }
    
    public void newGame(GamePanel gamePanel) {
    	mainPanel.add(gamePanel, "newgame");
    	stackOfScreen.push(new ScreenComponent(gamePanel, "newgame"));
    	
    	cardLayout.show(mainPanel, "loadingScreen");
        Timer timer = new Timer(1000, new ActionListener(){
        	  @Override
        	  public void actionPerformed( ActionEvent e ){
        		  cardLayout.show(mainPanel, "newgame");
        		  gamePanel.setVisible(true);
        		  gamePanel.requestFocusInWindow();
        		  gamePanel.startGameThread();
        	  }
        	} );
        timer.setRepeats( false );
        timer.start();
        
    	
    }
    
    public void backScreen() {
    	stackOfScreen.pop();
    	screen = stackOfScreen.peek();
    	displayScreen(screen);
    }
    
    public void nextScreen() {
    	screen = stackOfScreen.peek();
    	displayScreen(screen);
    }
    
    void displayScreen(ScreenComponent screen) {
    	System.out.println(screen.screenName);
    	cardLayout.show(mainPanel, screen.screenName);
    }

    public static void main(String[] args) {
        DemoMain DemoMain = new DemoMain();
    }

}

class ScreenComponent {
	public JPanel screen;
	public String screenName;
	public ScreenComponent(JPanel screen, String screenName) {
		this.screen = screen;
		this.screenName = screenName;
	}
}