import javax.swing.event.PopupMenuListener;

public class TestClassForView {
	//To be used for testing the View-class. Will populate an array of tiles, add a robot and tell View to show it
	
	private static Tile[][] tiles;
	private int size = 16;
	
	public TestClassForView(){
		tiles = new Tile[size][size];
		populateTiles();
		View v = new View(size);		
		v.redraw(tiles);
	}

	public static void main(String[] args) {
		TestClassForView t = new TestClassForView();
	}
	
	//Adds tiles and a robot
	private void populateTiles() {
		for(int i=0; i< size; i++) {
			for(int j=0; j< size; j++) {
				tiles[i][j]= new Tile(new boolean[] {false},false,false);
			}
		}
		Tile t = tiles[2][5];
		t.setRobot(new Robot());
	}
	
}
