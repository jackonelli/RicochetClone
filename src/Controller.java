import java.util.ArrayList;
//import java.util.stream;

public class Controller
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
    //private ArrayList<Integer> unvisitedGoalz = IntStream.range(0, NUMBER_OF_GOALZ - 1).toArray();
    public Controller(Model model, View view){
        this.model = model; // Array
        this.view = view;
    }

    public void runGame(Model model, View view){
        //view.redraw(model);
        //getNewGoal();
        //TODO: Get from user input
        int robotID = YELLOW;
        int direction = NORTH;
        //move(robotID, direction);
    }

    private void move(int robotID, int direction){
        int tilesMoved = 0;
        boolean[] tileHasWalls = new boolean[4];
        boolean wallBlocks, tileHasRobot;
        int[] currentPosition = model.findRobot(robotID);
        int[] newPosition = new int[2];
        boolean canMove = true;
        int col, row;
        switch(direction){
            case NORTH :
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWallsFromTile(row + tilesMoved + 1, col);
                    wallBlocks = tileHasWalls[2];
                    tileHasRobot = model.tileHasRobot(row + tilesMoved + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                newPosition[0] = row + tilesMoved;
                newPosition[1] = col;
                //newPosition = {row + tilesMoved, col};

            case EAST :
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWallsFromTile(row, col + tilesMoved + 1);
                    wallBlocks = tileHasWalls[3];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row, col + tilesMoved + 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                newPosition[0] = row;
                newPosition[1] = col + tilesMoved;

            case SOUTH :
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWallsFromTile(row + tilesMoved + 1, col);
                    wallBlocks = tileHasWalls[1];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row + tilesMoved + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                newPosition[0] = row - tilesMoved;
                newPosition[1] = col;

            case WEST :
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWallsFromTile(row, col + tilesMoved + 1);
                    wallBlocks = tileHasWalls[2];
                    // Access tile?
                    tileHasRobot = model.tileHasRobot(row, col + tilesMoved + 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                newPosition[0] = row;
                newPosition[1] = col - tilesMoved;

        }
        updateBoard(robotID, newPosition);
    }

    private void updateBoard(int robotID, int[] newPosition){
        //model.setRobot(robotID, newPosition);
    }
}
