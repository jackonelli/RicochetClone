import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class TestClassForView implements ActionListener {
	//To be used for testing the View-class. Will populate an array of tiles, add a robot and tell View to show it
	
	private static Tile[][] tiles;
	private int size = 16;
	private View v;
	
	public TestClassForView(){
		tiles = new Tile[size][size];
		populateTiles();
		v = new View(size, this);		
		run();
		v.redraw(tiles);
		v.paintFlippedTile(1);
	}

	public static void main(String[] args) {
		TestClassForView t = new TestClassForView();
	}
	
	//Adds tiles and a robot
	private void populateTiles() {
		for(int i=0; i< size; i++) {
			for(int j=0; j< size; j++) {
				if (i==5 && j==7) {
					//Edit to test different walls
					tiles[i][j]= new Tile(new boolean[] {true,true,false,false},false,false);
				}else if(i==12 && j==12){
					//Edit to test goal tile
					tiles[i][j]= new Tile(new boolean[] {false,false,false,false},false,true);
				}else tiles[i][j]= new Tile(new boolean[] {false,false,false,false},false,false);
			}
		}
		Tile t1 = tiles[2][5];
		t1.setRobot(new Robot());
		
	}
	
	private void run() {
		Timer t = new Timer(100, new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				v.redraw(tiles);
				//v.setTime(10);
				//v.setScore(1000);
			}
		});
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Klicked Restart");
		//remove flipped tile
		v.paintFlippedTile(0);

		
		
	}
	
}
