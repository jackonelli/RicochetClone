
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class View extends JFrame {
	
	
	private int size;
	private GameArea theGameArea;
	private static int recWidth = 20;
	private static int recHeight  =20;
	private static int recPosX= 0;
	private static int recPosY = 0;
	private static Tile[][] tiles;
	
	
	public View() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1,2));
		
		//Code to use when testing
		testPaint();
		
		theGameArea = new GameArea();
		theGameArea.setBackground(Color.GRAY);
		JPanel infoTab = new JPanel();
		infoTab.setBackground(Color.BLACK);
		this.setSize(800, 500);
		this.add(theGameArea);
		this.add(infoTab);
	
		
		//this.pack();	
		this.setVisible(true);		
		
	}
	
	//To use for test the paint funcitonality
	private void testPaint() {
		tiles = new Tile[16][16]; 
		Tile t1 = new Tile(new boolean[] {false},false,false);
		Tile t2 = new Tile(new boolean[] {false},true,false);
		
		tiles[0][0] = t1;
		tiles[1][0] = t2;
		redraw(tiles);
		
	}
	
	public static class GameArea extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponents(g);
			
			int recPosX = 0;
			int recPosY = 0;
	
			for(Tile[] t: tiles) {
				for (Tile val: t) {
					recPosX = recPosX + recWidth;
					g.setColor(Color.GRAY);
				    g.drawRect(recPosX, recPosY, recWidth, recHeight);
				   
				}
				recPosX = 0;
				recPosY = recPosY + recHeight;
			}	
		}
	}

	public void redraw(Tile[][] tiles) {
		this.tiles = tiles;
	}		
}
