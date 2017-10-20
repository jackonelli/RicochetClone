import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements ActionListener
{
	int NR_OF_PLAYERS = 1;
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
    Timer theTimer;
    private int timerTime;
    String inputNumbers = "0";
  
    public Controller(){
    	initiateGame();
    	initiateKeyEventDispatcher();
    	
        //Using another way of tracking keypresses within Controller instead
        //view.addKeyListener((KeyListener) new KeyboardListener());
    }
    
    public void initiateGame(){
    	this.model = new Model(SIZE); // Array
    	populateGameBoard();
    	this.view = new View(SIZE,model.getTiles(),this);
    	theTimer = new Timer(1000, this);
        timerTime = 60;   
  		model.addRobots(createRobots());
        theRobot = model.getRobots()[0];  
        updateGUI();
    }

    private void sendKeyInputToView(int i) {
    	if (inputNumbers == "0") {
    		inputNumbers = Integer.toString(i);
    	}else {inputNumbers += Integer.toString(i);}
    	view.setMoves(inputNumbers);
    }
    
    private void checkIfRobotIsOnCorrectGoal() {
    	if (model.getTile(theRobot.getPosish().x, theRobot.getPosish().y).hasGoal()) {
    		System.out.println("Träff");
    	}
    }
    
    private void updateGUI() {
    	view.redraw(model.getTiles(), model.getRobots());
    }
    
    public void runGame(){
        //getNewGoal();
    	//updateGUI();
        //while true?
        //TODO: Get from user input
        int robotID = YELLOW;
        int direction = SOUTH;
        
        
    }
    /* Made this return a point for the MVP but we 
     * should input the actual robot, move it and
     * void output
     */
    Point currentPosition;
    Robot theRobot;
    boolean[] tileHasWalls = new boolean[4];
    boolean wallBlocks, tileHasRobot;
    boolean atBounds;
    boolean canMove;
    int col, row;
    private void move(int robotID, int direction){
        currentPosition = model.findRobot(robotID).getPosish();
        canMove = true;
        row = currentPosition.x;
        col = currentPosition.y;

        switch(direction){
            case NORTH :
                while(canMove){
                    atBounds = (row) == 0;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row, col);  
                    wallBlocks = tileHasWalls[0];
                    tileHasRobot = model.tileHasRobot(row - 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        row--;
                    }
                }
                moveRobot(theRobot,new Point(row, col));
                break;
                
            case EAST :
                while(canMove){
                    atBounds = (col) == SIZE;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row, col);
                    wallBlocks = tileHasWalls[1];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row, col+1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        col++;
                    }
                }
                moveRobot(theRobot,new Point(row, col));
                break;

            case SOUTH :
                while(canMove){
                    atBounds = (row) == SIZE;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row, col);
                    wallBlocks = tileHasWalls[2];
                    tileHasRobot = model.tileHasRobot(row + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        row++;
                    }
                }
                moveRobot(theRobot,new Point(row, col));
                break;

            case WEST :
                row = currentPosition.x;
                col = currentPosition.y;
                while(canMove){
                    atBounds = (col) == 0;
                    if(atBounds){
                        break;
                    }
                    tileHasWalls = model.getWallsFromTile(row, col);
                    wallBlocks = tileHasWalls[3];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row, col - 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        col--;
                    }
                }
                moveRobot(theRobot,new Point(row, col));
                break;    
        } // End switch 
    }

    private void moveRobot(Robot theRobot, Point currentPosition) {
    	theRobot.setPosish(currentPosition.x,currentPosition.y);
    	checkIfRobotIsOnCorrectGoal();
    	updateGUI();
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
		Robot[] bufferList = new Robot[2];
		Robot r1 = new Robot();
		Robot r2 = new Robot();
		r1.setPosish(2, 1);
		r1.setID(RED);
		r2.setPosish(4, 7);
		r2.setID(BLUE);
		bufferList[0] = r1;
		bufferList[1] = r2;
		
		return bufferList;
	}
  	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getID() == 1001) {
			theTimer.stop();
			view.setTimerTime(timerTime = 60);	
			view.setMoves("0");
		}
		if (e.getID()==0) {
			timerTick();
		}
	}
	
	private void timerTick() {
		timerTime --;
		view.setTimerTime(timerTime);
	}
	
	private void initiateKeyEventDispatcher() {
    	KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
            	
                if(e.getID() == KeyEvent.KEY_PRESSED){
	                int keyCode = e.getKeyCode();
	                
	                switch ( keyCode ) {
	                	case KeyEvent.VK_UP:
		                	move(theRobot.getId(), NORTH);		                    
	                    break;
	                	case KeyEvent.VK_DOWN:
	                		move(theRobot.getId(), SOUTH);
	                    break;
	                	case KeyEvent.VK_LEFT:
	                		move(theRobot.getId(), WEST);
	                    break;
	                	case KeyEvent.VK_RIGHT :
	                		move(theRobot.getId(), EAST);
	                    break;
	                	case KeyEvent.VK_0 :
	                		sendKeyInputToView(0);
	                    break;
	                	case KeyEvent.VK_1 :
	                		sendKeyInputToView(1);
	                    break;
	                	case KeyEvent.VK_2 :
	                		sendKeyInputToView(2);
	                    break;
	                	case KeyEvent.VK_3 :
	                		sendKeyInputToView(3);
	                    break;
	                	case KeyEvent.VK_4 :
	                		sendKeyInputToView(4);
	                    break;
	                	case KeyEvent.VK_5 :
	                		sendKeyInputToView(5);
	                    break;
	                	case KeyEvent.VK_6 :
	                		sendKeyInputToView(6);
	                    break;
	                	case KeyEvent.VK_7 :
	                		sendKeyInputToView(7);
	                    break;
	                	case KeyEvent.VK_8 :
	                		sendKeyInputToView(8);
	                    break;
	                	case KeyEvent.VK_9 :
	                		sendKeyInputToView(9);
	                    break;
	                	case KeyEvent.VK_ENTER :
	                		inputNumbers = "0";
	                		theTimer.start();
	                    break;
	                }
                }
                return false;
            }
        });
    }
}
