package tetris;

import ActionListeners.MenuListener;
import ActionListeners.KeyboardListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author JacksonTrudel
 */
public class TetrisUI {
    Game game;
    JFrame container;
    JLabel cp;
    JPanel gamePanel;
    JPanel nextPanel;
    JLabel scoreLabel;  //makes up "scorePanel"
    JLabel numberLabel; //  ^^
    Font labelFont = new Font("TimesRoman", Font.BOLD, 55);
    Color cpColor = new Color(10, 73, 82);
    Color labelFontColor = new Color(186, 122, 95);
    Color GRAY = new Color(64, 71, 71);
    Square nextGrid[][];
    
    
    
    Square gameGrid[][];
    JMenuBar mb;
    JMenu menu;
    JMenuItem newGameMI;
    JMenuItem quitMI;
    JMenuItem pauseMI;
    MenuListener pauseML;
    MenuListener newGameML;
    MenuListener quitML;
    Image img;
    ImageIcon myImage;
    
    
    
    // Initializes the UI
    public TetrisUI(Game game){
        this.game = game;
        
        //Initialize container for game
        initContainer();
        
        // Initializes Components
        initComponents();
        
        //init menu
        initMenu();
        
    }
    
    // Initializes the JFrame and content pane
    private void initContainer(){
		
        container = new JFrame("Tetris");
		
        // init content pane with background image
        String imgURL = "../images/background.jpg";
        myImage = new ImageIcon( getClass().getResource(imgURL));
        img = myImage.getImage();
        img = getScaledImage(img, 1300, 1900);
        myImage = new ImageIcon(img);
        cp = new JLabel(myImage);
        cp.setPreferredSize(new Dimension(1300, 1900));
        cp.setLayout(null);
        container.setContentPane(cp);
        
        
        // finalize container
        container.pack();
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setResizable(false);
        container.setVisible(true);
    }
    
    // Initializes the components and adds them to the content pane
    private void initComponents(){
        //init nextLabel
        JLabel nextLabel = new JLabel("NEXT:", SwingConstants.CENTER);
        nextLabel.setFont(labelFont);
        nextLabel.setForeground( labelFontColor );
        nextLabel.setBackground( GRAY );
        nextLabel.setOpaque(true);
        nextLabel.setBounds(50, 50, 400, 150);
        cp.add(nextLabel);
        
        //init nextPanel
        initNextPanel();   
        cp.revalidate();
        cp.repaint();
        
        
        // init gamePanel
        initGamePanel();
        cp.revalidate();
        cp.repaint();
        
        //initScorePanel
        initScorePanel();
        cp.revalidate();
        cp.repaint();
        
        container.addKeyListener(new KeyboardListener(game));
        game.setGameBoard(gameGrid);
        game.setNextBoard(nextGrid);
        game.setScoreLabel(numberLabel);
        game.setUI(this);
        

    }
    
    // Initializes display for the next shape
    private void initNextPanel(){
        nextPanel = new JPanel();
        nextPanel.setLayout(null);
        nextPanel.setBounds(50, 200, 400, 300);
        nextGrid = new Square[3][4];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                nextGrid[i][j] = new Square();
                nextGrid[i][j].setBounds((j*100), (i*100), 100, 100);
                nextPanel.add(nextGrid[i][j]);
            }
        }
        System.out.println("Here");
        cp.add(nextPanel);
    }
    
    // Initializes display of the game board
    private void initGamePanel(){
        gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setBounds(500, 50, 750, 1800);
        gameGrid = new Square[24][10];
        for(int i = 0; i < 24; i++){ 
            for(int j = 0; j < 10; j++){
                gameGrid[i][j] = new Square();
                gameGrid[i][j].setBounds((j*75), (i*75), 75, 75);
                gamePanel.add(gameGrid[i][j]);
            }
        }
        
        cp.add(gamePanel);
    }
    
    // Initializes the JLabel(s) displaying the score
    // there is no actual "scorePanel" btw
    private void initScorePanel(){
        //score label
        scoreLabel = new JLabel( "SCORE", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("TimesRoman", Font.BOLD, 70));
        scoreLabel.setForeground( labelFontColor );
        scoreLabel.setBackground( GRAY );
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(50, 1400, 400, 150);
        //scoreLabel.setHorizontalTextPosition(JLabel.CENTER);
        cp.add(scoreLabel);
        
        //number label
        numberLabel = new JLabel( Integer.toString(game.getScore()) , SwingConstants.CENTER );
        numberLabel.setFont(new Font("TimesRoman", Font.BOLD, 100));
        numberLabel.setForeground( labelFontColor );
        numberLabel.setBackground( GRAY );
        numberLabel.setOpaque(true);
        numberLabel.setBounds(50, 1550, 400, 300);
        //scoreLabel.setHorizontalTextPosition(JLabel.CENTER);
        cp.add(numberLabel);
        
        
        
        
    }
    
    
    // Initializes and adds the menu bar
    private void initMenu(){
        Font menuFont = new Font("TimesRoman", Font.BOLD, 32);
        mb = new JMenuBar();
        mb.setPreferredSize(new Dimension(100, 50));
        menu = new JMenu("Menu");
        pauseMI = new JMenuItem("Pause Game");
        pauseMI.setPreferredSize(new Dimension(300, 50));
        pauseMI.setFont(menuFont);
        newGameMI = new JMenuItem("New Game");
        newGameMI.setPreferredSize(new Dimension(300, 50));
        newGameMI.setFont(menuFont);
        quitMI = new JMenuItem("Quit");
        quitMI.setPreferredSize(new Dimension(300, 50));
        quitMI.setFont(menuFont);
        menu.add(pauseMI);
        //menu.add(newGameMI);
        menu.add(quitMI);
        menu.setFont(menuFont);
        mb.add(menu);
        container.setJMenuBar(mb);
        
        //init menu listeners
        newGameML  = new MenuListener(this, newGameMI, 1);
        quitML = new MenuListener(this, quitMI, 2);
        pauseML = new MenuListener(this, pauseMI, 0);
    }
    
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    public void pauseGame(){
        game.pause();
    }
    
    public void quitGame(){
        game.quit();
        displayEndGame();
    }
    
    // Displays screen with final score
    private void displayEndGame(){
        int score = game.getScore();
        JLabel endDisplay = new JLabel(myImage);
        
        endDisplay.setPreferredSize(new Dimension(1300, 1900));
        endDisplay.setLayout(null);
        
        
        JLabel scoreDisplay = new JLabel("Final Score: " + Integer.toString(score), SwingConstants.CENTER );
        scoreDisplay.setFont(new Font("TimesRoman", Font.BOLD, 80));
        scoreDisplay.setForeground( labelFontColor );
        scoreDisplay.setBackground( GRAY );
        scoreDisplay.setOpaque(true);
        scoreDisplay.setBounds(250, 700, 800, 500);
        endDisplay.add(scoreDisplay);
        
        container.setContentPane(endDisplay);
        
        
        
        container.pack();
        container.repaint();
        container.revalidate();
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setResizable(false);
        container.setVisible(true);
        
    }
    
    
}
