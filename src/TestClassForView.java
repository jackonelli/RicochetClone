import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class TestClassForView {
	//To be used for testing the View-class. Will populate an array of tiles, add a robot and tell View to show it
	
	private static Tile[][] tiles;
	private int size = 16;
	private View v;
	
	public TestClassForView(){
		tiles = new Tile[size][size];
		populateTiles();
		v = new View(size);		
		run();
		v.redraw(tiles);
		v.redraw(tiles);
	}

	public static void main(String[] args) {
		TestClassForView t = new TestClassForView();
	}
	
	//Adds tiles and a robot
	private void populateTiles() {
		for(int i=0; i< size; i++) {
			for(int j=0; j< size; j++) {
				if (i==5 && j==7) {
					//Editera för att testa olika kombinationer för väggar
					tiles[i][j]= new Tile(new boolean[] {true,true,false,false},false,false);
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
			}
		});
		t.start();
		
		
	}
	
}
