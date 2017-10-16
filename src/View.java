
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	
	private int size;
	private static int recWidth;
	private static int recHeight;
	private static Tile[][] tiles;
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
    private static Image wallTileImage;
    private static final String ROBOT_IMAGE_URL ="assets/graphics/layers/RedRobot.png";
    private static final String EMPTY_TILE_IMAGE_URL ="assets/graphics/layers/Tile.png";   
    private static final String N_TILE_IMAGE_URL ="assets/graphics/layers/WallNorth.png";
    private static final String S_TILE_IMAGE_URL ="assets/graphics/layers/WallSouth.png";
    private static final String W_TILE_IMAGE_URL ="assets/graphics/layers/WallWest.png";
    private static final String E_TILE_IMAGE_URL ="assets/graphics/layers/WallEast.png";
	
    /**
     * @param size Define the size in tiles of the board e.g. 16 -> 16x16 (16 is recommended)
     */
	public View(int size) {
		this.size = size;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowSize);
		this.setResizable(false);
		recWidth = (gameAreaSize/size);
		recHeight = (gameAreaSize/size);
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
					//Draw Wall - nu exempel för en vägg i norr
					checkIfDrawWall(walls, g2d);
					//Draw Robot
					if(val.hasRobot()==true){
						drawRobotTile(g2d);
					}
					recPosX = recPosX + recWidth;
				}
				recPosX = 0;
				recPosY = recPosY + recHeight;
			}
			
		}
		private void checkIfDrawWall(boolean[] b, Graphics2D g2d) {
			if(walls[0]) drawNorthTile(g2d);
	
			if(walls[1]) drawEastTile(g2d);
			
			if(walls[2]) drawSouthTile(g2d);
			
			if(walls[3]) drawWestTile(g2d);
		}
		
		private void drawNorthTile(Graphics2D g2d) {
			g2d.drawImage(nTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawEastTile(Graphics2D g2d) {
			g2d.drawImage(eTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawSouthTile(Graphics2D g2d) {
			g2d.drawImage(sTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawWestTile(Graphics2D g2d) {
			g2d.drawImage(wTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawEmptyTile(Graphics2D g2d) {
			g2d.drawImage(emptyTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawRobotTile(Graphics2D g2d) {
			g2d.drawImage(robotImage, recPosX, recPosY, recWidth, recHeight, this);
		}	
	}
	
	public static class InfoTab extends JPanel{
		public InfoTab() {
			Font theFont = new Font("Courier New", Font.ITALIC, 24);
			JLabel timeTextLable = new JLabel("Time left: ");
			timeTextLable.setFont(theFont);
			JLabel timeValueLable = new JLabel("1:00:00");
			timeValueLable.setFont(theFont);
			JLabel scoreTextLable = new JLabel("Score: ");
			scoreTextLable.setFont(theFont);
			JLabel scoreValueLable = new JLabel("0");
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
	}
}
