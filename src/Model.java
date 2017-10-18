import java.awt.Point;

public class Model {
	private Tile[][] tiles;
	private Robot[] robots ;
	
	public Model () {
		this.tiles = new Tile[16][16];
	}
	
	public Model (int size) {
		this.tiles = new Tile[size][size];
	}
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public void setTiles(Tile[][] gameBoard) {
		tiles = gameBoard;
	}
	
	public Robot[] getRobots() {
		return robots;
	}
	
	public void addRobots(Robot[] robots){
		this.robots = robots;
	}

	public boolean[] getWallsFromTile (int row, int col) {
		return tiles[row][col].getWalls();
	}

    public boolean tileHasRobot(int row, int col){
        Point tilePosition = new Point(row, col);
        for(Robot robot : robots){
            if(tilePosition.equals(robot.getPosish())){
                return true;
            }
        }
        return false;
    }

	// public int[] findRobot(int RobotId) {
		
	// 	for (int i = 0; i < tiles.length; i++) { 
	// 		for (int j = 0; j < tiles[j].length; j++) { 
	// 			if (id == tiles[i][j].getRobot().getId()) {
	// 				int[] position = {i,j};
	// 				return position;
	// 			}
	// 		}
	// 	}
	// 	int[] empty = {0,0};
	// 	return empty;

	// }
	public void setRobot(int robotId, int row, int col){
		for (Robot robot : robots) {
			if (robot.getId() == robotId){
				robot.setPosish(row, col);
			}
	    }
    }
	public Robot findRobot(int robotId){

		Robot tempRobot = new Robot();

		for (Robot robot : robots) {
			if (robot.getId() == robotId){
				tempRobot = robot; 
			}
		}
		return tempRobot;
    }
}
