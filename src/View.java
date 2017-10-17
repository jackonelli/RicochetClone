
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class View extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO: Found that if window is moved outside of screen, gets repainted	/Niklas
	
	private static int tileWidth;
	private static int tileHeight;
	private Tile[][] tiles;
	private final JSplitPane splitPane;
	private final JPanel leftPart;
	private final GameArea theGameArea;	// container panel for the GameAreA
    private final InfoTab infoTab;	// container panel for the info
    private int gameAreaSize = 650;
    private int leftPaneWidth = gameAreaSize + 300;
    private Dimension windowSize = new Dimension (1280,720); //Dimensions of the window
    private static Image robotImage;
    private static Image emptyTileImage;
    private static Image nTileImage;
    private static Image sTileImage;
    private static Image wTileImage;
    private static Image eTileImage;
    private static Image middleTileImage;
    private static Image goalTileImage;
    private static final String ROBOT_IMAGE_URL ="assets/graphics/layers/RedRobot.png";
    private static final String EMPTY_TILE_IMAGE_URL ="assets/graphics/layers/Tile.png";   
    private static final String N_TILE_IMAGE_URL ="assets/graphics/layers/WallNorth.png";
    private static final String S_TILE_IMAGE_URL ="assets/graphics/layers/WallSouth.png";
    private static final String W_TILE_IMAGE_URL ="assets/graphics/layers/WallWest.png";
    private static final String E_TILE_IMAGE_URL ="assets/graphics/layers/WallEast.png";
    private static final String MIDDLE_TILE_IMAGE_URL ="assets/graphics/layers/MiddleTile.png";
    private static final String GOAL_TILE_IMAGE_URL ="assets/graphics/layers/Goal.png";
    private JLabel timeValueLable;
    private JLabel scoreValueLable;
    private ActionListener al;
    private int flippedTile;
   
	
    /**
     * @param size Define the size in tiles of the board e.g. 16 -> 16x16 (16 is recommended)
     * @param actionListener - an ActionListener for receiving e.g. restart button clicks
     */
	public View(int size, ActionListener actionListener) {
		this.al = actionListener;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowSize);
		this.setResizable(false);
		tileWidth = (gameAreaSize/size);
		tileHeight = (gameAreaSize/size);
		splitPane = new JSplitPane();
		getContentPane().setLayout(new GridLayout());
		getContentPane().add(splitPane); 
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(leftPaneWidth); 
		splitPane.setDividerSize(1);
		splitPane.setEnabled(false);
		leftPart = new JPanel();
		leftPart.setBackground(Color.WHITE);
		theGameArea = new GameArea();		
		infoTab = new InfoTab();
		infoTab.setBackground(Color.GRAY);
		splitPane.setLeftComponent(leftPart);
		splitPane.setRightComponent(infoTab);
		leftPart.setLayout(new GridBagLayout());
		leftPart.add(theGameArea);
		
		try {
			importImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setVisible(true);	
		
	}
	
	public class GameArea extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public GameArea() {
			this.setBackground(Color.GRAY);
			this.setPreferredSize(new Dimension(gameAreaSize,gameAreaSize));
		}
		
		int recPosX = 0;
		int recPosY = 0;
		boolean[] walls;
		public void paint(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D) g;
			
			 recPosX = 0;
			 recPosY = 0;
			 walls = null;
			
			//Decide what and where to draw
			for(Tile[] t: tiles) {
				for (Tile val: t) {
					walls = val.getWalls();
					//Draw tile
					drawEmptyTile(g2d);
					//Draw Wall 
					checkIfDrawWall(walls, g2d);
					//Draw Robot
					if(val.hasRobot()){
						drawRobotTile(g2d);
					}
					if(val.hasGoal()) {
						drawGoalTile(g2d);
					}
					recPosX = recPosX + tileWidth;
				}
				recPosX = 0;
				recPosY = recPosY + tileHeight;
			}
			drawMiddleTile(g2d);
			drawFlippedTile(g2d);
		}
		private void checkIfDrawWall(boolean[] b, Graphics2D g2d) {
			if(walls[0]) drawNorthTile(g2d);
	
			if(walls[1]) drawEastTile(g2d);
			
			if(walls[2]) drawSouthTile(g2d);
			
			if(walls[3]) drawWestTile(g2d);
		}
		
		private void drawNorthTile(Graphics2D g2d) {
			g2d.drawImage(nTileImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawEastTile(Graphics2D g2d) {
			g2d.drawImage(eTileImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawSouthTile(Graphics2D g2d) {
			g2d.drawImage(sTileImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawWestTile(Graphics2D g2d) {
			g2d.drawImage(wTileImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawEmptyTile(Graphics2D g2d) {
			g2d.drawImage(emptyTileImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawRobotTile(Graphics2D g2d) {
			g2d.drawImage(robotImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawGoalTile(Graphics2D g2d) {
				g2d.drawImage(goalTileImage, recPosX, recPosY, tileWidth, tileHeight, this);
		}
		private void drawFlippedTile(Graphics2D g2d) {
			if(flippedTile ==1) {
				g2d.drawImage(middleTileImage, (int)(7*tileWidth+0.5*tileWidth),(int)(7*tileHeight+0.5*tileHeight), tileWidth, tileHeight, this);
				g2d.drawImage(goalTileImage, (int)(7*tileWidth+0.5*tileWidth),(int)(7*tileHeight+0.5*tileHeight), tileWidth, tileHeight, this);
			}		
		}
		private void drawMiddleTile(Graphics2D g2d) {
			g2d.drawImage(middleTileImage, (7*tileWidth), (7*tileHeight), tileWidth, tileHeight, this);
			g2d.drawImage(middleTileImage, (8*tileWidth), (7*tileHeight), tileWidth, tileHeight, this);
			g2d.drawImage(middleTileImage, (7*tileWidth), (8*tileHeight), tileWidth, tileHeight, this);
			g2d.drawImage(middleTileImage, (8*tileWidth), (8*tileHeight), tileWidth, tileHeight, this);
		}
	}
	
	public class InfoTab extends JPanel{
		
		public InfoTab() {
			Font theFont = new Font("Courier New", Font.ITALIC, 24);
			JLabel timeTextLable = new JLabel("Time left: ");
			timeTextLable.setFont(theFont);
			timeValueLable = new JLabel("1:00:00");
			timeValueLable.setFont(theFont);
			JLabel scoreTextLable = new JLabel("Score: ");
			scoreTextLable.setFont(theFont);
			scoreValueLable = new JLabel("0");
			scoreValueLable.setFont(theFont);
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Define area for spacing to top
			JPanel spaceTop = new JPanel();
			spaceTop.setMaximumSize(new Dimension(300, 50));
			
			//Define area for time
			JPanel timeContainer = new JPanel();
			timeContainer.setMaximumSize(new Dimension(300, 50));
			timeContainer.add(timeTextLable);
			timeContainer.add(timeValueLable);
			
			//Define area for score
			JPanel scoreContainer = new JPanel();
			scoreContainer.setMaximumSize(new Dimension(300, 50));
			scoreContainer.add(scoreTextLable);
			scoreContainer.add(scoreValueLable);
			
			//Define restart button
			Button restartButton = new Button("Restart");
			restartButton.setMaximumSize(new Dimension(300,50));
			restartButton.setFont(theFont);
			restartButton.addActionListener(al);
			
			//Add all items
			this.add(spaceTop);
			this.add(timeContainer);
			this.add(scoreContainer);
			this.add(restartButton);
		}
	}
	
	/**
	 * 
	 * @param tiles - 2d array of tiles showing current state of gameboard
	 */
	public void redraw(Tile[][] tiles) {
		this.tiles = tiles;
		this.repaint();
	}		
	
	private void importImages() throws IOException {
		BufferedImage importImg;
		importImg  = ImageIO.read(new File(ROBOT_IMAGE_URL));
		robotImage = importImg;
		importImg  = ImageIO.read(new File(EMPTY_TILE_IMAGE_URL));
		emptyTileImage = importImg;
		importImg  = ImageIO.read(new File(N_TILE_IMAGE_URL));
		nTileImage = importImg;
		importImg  = ImageIO.read(new File(S_TILE_IMAGE_URL));
		sTileImage = importImg;
		importImg  = ImageIO.read(new File(W_TILE_IMAGE_URL));
		wTileImage = importImg;
		importImg  = ImageIO.read(new File(E_TILE_IMAGE_URL));
		eTileImage = importImg;
		importImg  = ImageIO.read(new File(GOAL_TILE_IMAGE_URL));
		goalTileImage = importImg;
		importImg  = ImageIO.read(new File(MIDDLE_TILE_IMAGE_URL));
		middleTileImage = importImg;
	}
	/**
	 * @param time - the current time in the game
	 */
	public void setTime(int time) {
		timeValueLable.setText(String.valueOf(time));
	}
	
	/**
	 * @param score - the current score of the player
	 */
	public void setScore(int score) {
		scoreValueLable.setText(String.valueOf(score));
	}
	
	/**
	 * 
	 * @param tileType - (int) 1 = star (only one supported atm)
	 */
	public void paintFlippedTile(int tileType) {
		flippedTile = tileType;
	}
	
}
