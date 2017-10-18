import java.util.ArrayList;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener
{
    public final int SIZE = 16;
    public final int NUMBER_OF_ROBOTS = 5;
    public final int NUMBER_OF_GOALZ = 17;
    public final int NORTH = 1;
    public final int EAST = 2;
    public final int SOUTH = 3;
    public final int WEST = 4;
    public final int BLUE = 1;
    public final int RED = 2;
    public final int GREEN = 3;
    public final int YELLOW = 4;
    public final int SILVER = 5;
    private Model model;
    private View view;
    private ArrayList<Integer> unvisitedGoalz = new ArrayList<Integer>();
    
    public Controller(){
    	this.model = new Model(SIZE); // Array
    	populateGameBoard();
        this.view = new View(SIZE,model.getTiles(),this);
    }

    public void runGame(){
        //getNewGoal();
    	
        view.redraw(model.getTiles(), model.getRobots());
        //while true?
        //TODO: Get from user input
        int robotID = YELLOW;
        int direction = SOUTH;
        Robot robot = model.getRobots()[0];
        Point newPosish = move(robot.getId(), direction);
        robot.setPosish(newPosish.x, newPosish.y);
        view.redraw(model.getTiles(), model.getRobots());
    }
    /* Made this return a point for the MVP but we 
     * should input the actual robot, move it and
     * void output
     */
    private Point move(int robotID, int direction){
        Robot movingRobot = model.findRobot(robotID);
        Point currentPosition = movingRobot.getPosish();
        System.out.println(currentPosition);
        Point newPosition = new Point();
        boolean[] tileHasWalls = new boolean[4];
        boolean wallBlocks, tileHasRobot;
        boolean atBounds;
        boolean canMove = true;
        int col, row;
        switch(direction){
            case NORTH :
                row = currentPosition.x;
                col = currentPosition.y;
                while(canMove){
                    atBounds = (row) == 0;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row - 1, col);
                    wallBlocks = tileHasWalls[2];
                    tileHasRobot = model.tileHasRobot(row - 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        row--;
                    }
                }
                newPosition.setLocation(row, col);
                break;

            case EAST :
                row = currentPosition.x;
                col = currentPosition.y;
                while(canMove){
                    atBounds = (col) == SIZE;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row, col + 1);
                    wallBlocks = tileHasWalls[3];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row, col + 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        col++;
                    }
                }
                newPosition.setLocation(row, col);
                break;

            case SOUTH :
                row = currentPosition.x;
                col = currentPosition.y;
                while(canMove){
                    atBounds = (row) == SIZE;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row + 1, col);
                    wallBlocks = tileHasWalls[1];
                    System.out.println(row);
                    System.out.println(wallBlocks);
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        row++;
                    }
                }
                newPosition.setLocation(row, col);
                break;

            case WEST :
                row = currentPosition.x;
                col = currentPosition.y;
                while(canMove){
                    atBounds = (col) == 0;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row, col - 1);
                    wallBlocks = tileHasWalls[2];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row, col - 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        col--;
                    }
                }
                newPosition.setLocation(row, col);
                break;
        } // End switch
        return newPosition;
    }

    private void updateBoard(int robotID, int[] newPosition){
        //model.setRobot(robotID, newPosition);
    }
    //Adds tiles, goals and a robots
  	private void populateGameBoard() {
  		Tile[][] gameBoard = new Tile[SIZE][SIZE];
  		for(int i=0; i< SIZE; i++) {
  			for(int j=0; j< SIZE; j++) {
  				gameBoard[i][j]= new Tile(new boolean[] {false,false,false,false},false,false);
  			}
  		}
  		gameBoard = createWalls(gameBoard);
  		gameBoard = createGoals(gameBoard);
  		model.addRobots(createRobots());
  		//model.setRobot(robotListBuffer[0].getId(), robotListBuffer[0].getPosish().x, robotListBuffer[0].getPosish().y);
  		model.setTiles(gameBoard);
  		
  		
  	}
  	
  	private Tile[][] createWalls(Tile[][] gameBoard) {
  		//Add north walls on top border of gamearea
  		for(int i=1; i< SIZE-1; i++) {
  			gameBoard[0][i] = new Tile(new boolean[] {true,false,false,false},false,false); 
  		}
  		//Add east walls on right border of gamearea
  		for(int i=1; i< SIZE-1; i++) {
  			gameBoard[i][SIZE-1] = new Tile(new boolean[] {false,true,false,false},false,false); 
  		}
  		//Add south walls on bottom border of gamearea
  		for(int i=1; i< SIZE-1; i++) {
  			gameBoard[SIZE-1][i] = new Tile(new boolean[] {false,false,true,false},false,false); 
  		}
  		//Add west walls on left border of gamearea
  		for(int i=1; i< SIZE-1; i++) {
  			gameBoard[i][0] = new Tile(new boolean[] {false,false,false,true},false,false); 
  		}
  		//Add walls to the corners of the gamearea
  		gameBoard[0][0] = new Tile(new boolean[] {true,false,false,true},false,false);
  		gameBoard[0][SIZE-1] = new Tile(new boolean[] {true,true,false,false},false,false);
  		gameBoard[SIZE-1][SIZE-1] = new Tile(new boolean[] {false,true,true,false},false,false);
  		gameBoard[SIZE-1][0] = new Tile(new boolean[] {false,false,true,true},false,false);
  		
  		Tile theTile;
  		//Add south walls to tiles within the outer border
  		Point[] southWalls = new Point[] {new Point(1,13), new Point(1,15), new Point(2,5), new Point(2,9), new Point(3,2), new Point(3,14), new Point(4,0), new Point(5,1), new Point(5,7), new Point(6,7), new Point(6,8), new Point(6,12), new Point(8,7), new Point(8,8), new Point(9,4), new Point(9,6), new Point(9,13), new Point(10,0), new Point(11,7), new Point(11,9), new Point(11,15), new Point(12,1), new Point(12,14), new Point(13,10), new Point(14,3)};
  		for(Point thePoint: southWalls) {
  			theTile = gameBoard[(int) thePoint.getX()][(int) thePoint.getY()];
  			boolean[] thePointsWalls = theTile.getWalls();
  			//Add south wall
  			thePointsWalls[2] = true;
  			gameBoard[(int) thePoint.getX()][(int) (thePoint.getY())] = new Tile(thePointsWalls,false,false);
  		}
  		
  		//Add south walls to all tiles with a north wall within the outer border
  		for(Point thePoint: southWalls) {
  			theTile = gameBoard[(int) thePoint.getX()+1][(int) thePoint.getY()];
  			boolean[] thePointsWalls = theTile.getWalls();
  			//Add north wall
  			thePointsWalls[0] = true;
  			gameBoard[(int) thePoint.getX()+1][(int) (thePoint.getY())] = new Tile(thePointsWalls,false,false);
  		}
  		
  		//Add east walls to tiles within the outer border
  		Point[] eastWalls = new Point[] {new Point(0,3), new Point(0,9), new Point(1,13), new Point(2,5), new Point(3,8), new Point(4,2), new Point(4,14), new Point(5,6), new Point(6,0), new Point(6,11), new Point(7,6), new Point(7,8), new Point(8,6), new Point(8,8), new Point(9,3), new Point(9,12), new Point(10,5), new Point(11,9), new Point(12,7), new Point(13,1), new Point(13,14), new Point(14,3), new Point(14,9), new Point(15,4), new Point(15,11)};
  		for(Point thePoint: eastWalls) {
  			theTile = gameBoard[(int) thePoint.getX()][(int) thePoint.getY()];
  			boolean[] thePointsWalls = theTile.getWalls();
  			//Add east wall
  			thePointsWalls[1] = true;
  			gameBoard[(int) thePoint.getX()][(int) (thePoint.getY())] = new Tile(thePointsWalls,false,false);
  		}
  		//Add west walls to all tiles with an east wall within the outer border
  		for(Point thePoint: eastWalls) {
  			theTile = gameBoard[(int) thePoint.getX()][(int) thePoint.getY()+1];
  			boolean[] thePointsWalls = theTile.getWalls();
  			//Add west wall
  			thePointsWalls[3] = true;
  			gameBoard[(int) thePoint.getX()][(int) (thePoint.getY()+1)] = new Tile(thePointsWalls,false,false);
  		}
  		return gameBoard;
  	}
  	
  	private Tile[][] createGoals(Tile[][] gameBoard) {
  		Point[] theGoals = new Point[] {new Point(2,5)};
  		for (Point theGoalsPoint: theGoals) {
  			Tile t = gameBoard[(int) theGoalsPoint.getX()][(int) theGoalsPoint.getY()];
  			//Add goal
  			t.setGoal();
  		}
  		return gameBoard;
  	}
  	
  	private Robot[] createRobots() {		
		Robot[] bufferList = new Robot[1];
		Robot r1 = new Robot();
		r1.setPosish(2, 1);
		bufferList[0] = r1;
		return bufferList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
