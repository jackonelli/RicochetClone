
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
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
    private final JPanel infoTab;	// container panel for the info
    private int gameAreaSize = 650;
    private int leftPaneWidth = gameAreaSize + 300;
    private Dimension windowSize = new Dimension (1280,720); //Dimensions of the window
    private static Image robotImage;
    private static Image emptyTileImage;
    private static Image nTileImage;
    private static Image sTileImage;
    private static Image wTileImage;
    private static Image eTileImage;
    private static final String ROBOT_IMAGE_URL ="src/img/imgRobot_v1.png";
    private static final String EMPTY_TILE_IMAGE_URL ="src/img/imgTile_v1.png";
    private static final String N_TILE_IMAGE_URL ="src/img/imgTile_N_v1.png";
    private static final String S_TILE_IMAGE_URL ="src/img/imgTile_S_v1.png";
    private static final String W_TILE_IMAGE_URL ="src/img/imgTile_W_v1.png";
    private static final String E_TILE_IMAGE_URL ="src/img/imgTile_E_v1.png";
	
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
		theGameArea.setBackground(Color.GRAY);
		theGameArea.setPreferredSize(new Dimension(gameAreaSize,gameAreaSize));
		infoTab = new JPanel();
		infoTab.setBackground(Color.GRAY);
		splitPane.setLeftComponent(leftPart);
		splitPane.setRightComponent(infoTab);
		leftPart.setLayout(new GridBagLayout());
		theGameArea.setSize(100,100);
		leftPart.add(theGameArea);
		
		try {
			importImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setVisible(true);	
		
	}
	
	public static class GameArea extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int recPosX = 0;
		int recPosY = 0;
		boolean[] walls;
		public void paint(Graphics g) {
			super.paintComponents(g);
			 recPosX = 0;
			 recPosY = 0;
			 walls = null;
			
			//Decide what and where to draw
			for(Tile[] t: tiles) {
				for (Tile val: t) {
					walls = val.getWalls();
					//Draw tile
					drawEmptyTile(g);
					//Draw Wall - nu exempel för en vägg i norr
					checkIfDrawWall(walls, g);
					//Draw Robot
					if(val.hasRobot()==true){
						drawRobotTile(g);
					}
					recPosX = recPosX + recWidth;
				}
				recPosX = 0;
				recPosY = recPosY + recHeight;
			}
			
		}
		private void checkIfDrawWall(boolean[] b, Graphics g) {
			if(walls[0]) drawNorthTile(g);
	
			if(walls[1]) drawEastTile(g);
			
			if(walls[2]) drawSouthTile(g);
			
			if(walls[3]) drawWestTile(g);
		}
		
		private void drawNorthTile(Graphics g) {
			g.drawImage(nTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawEastTile(Graphics g) {
			g.drawImage(eTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawSouthTile(Graphics g) {
			g.drawImage(sTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawWestTile(Graphics g) {
			g.drawImage(wTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawEmptyTile(Graphics g) {
			g.drawImage(emptyTileImage, recPosX, recPosY, recWidth, recHeight, this);
		}
		private void drawRobotTile(Graphics g) {
			g.drawImage(robotImage, recPosX, recPosY, recWidth, recHeight, this);
		}	
	}
	
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
