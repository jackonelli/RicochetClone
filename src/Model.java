
public class Model {
	private Tile[][] tiles;
	
	public Model () {
		this.tiles = new Tile[16][16];
	}
	
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public boolean[] getWallsFromTile (int row, int col) {
		return tiles[row][col].getWalls();
	}
	
	public boolean tileHasRobot(int row, int col) {
		return tiles[row][col].hasRobot();
	}
	
	public int[] findRobot(int id) {
		
		for (int i = 0; i < tiles.length; i++) { 
			for (int j = 0; j < tiles[j].length; j++) { 
				if (id == tiles[i][j].getRobot().getId()) {
					int[] position = {1,2};
					return position;
				}
			}
		}
		int[] empty = {0,0};
		return empty;

	}
}
