
import java.awt.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class View extends JFrame {
	//TODO: Found that if window is moved outside of screen, gets repainted	/Niklas
	
	private int size;
	private static int recWidth;
	private static int recHeight;
	private static Tile[][] tiles;
	private final JSplitPane splitPane;
	private final JPanel leftPart;
	private final GameArea theGameArea;	// container panel for the GameAreA
    private final JPanel infoTab;	// container panel for the info
    private int gameAreaSize = 500;
    private int leftPaneWidth = gameAreaSize + 400;
    private Dimension windowSize = new Dimension (1280,720);
	
    /**
     * @param size Define the size in tiles of the board e.g. 16x16 (16 is recommended)
     */
	public View(int size) {
		
		this.size = size;
		recWidth = (gameAreaSize/size);
		recHeight = (gameAreaSize/size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowSize);
		this.setResizable(false);
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
		
		this.setVisible(true);	
		
	}
	
	public static class GameArea extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponents(g);
			System.out.println(tiles[0][0].getRobot());
			int recPosX = 0;
			int recPosY = 0;
			for(Tile[] t: tiles) {
				for (Tile val: t) {
					
					if(val.getRobot()== null){
						g.setColor(Color.GRAY);
						 g.drawRect(recPosX, recPosY, recWidth, recHeight);
					}else {
						g.setColor(Color.BLACK);
						g.fillRect(recPosX, recPosY, recWidth, recHeight);
					}
				    recPosX = recPosX + recWidth;
				}
				recPosX = 0;
				recPosY = recPosY + recHeight;
			}	
		}
	}
	
	public void redraw(Tile[][] tiles) {
		this.tiles = tiles;
		this.repaint();
	}		
}
