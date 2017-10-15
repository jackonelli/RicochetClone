import static Constants.*;

public class Controller
{
    private Model model(SIZE);
    private View view(SIZE);
    public Controller(Model model, View view){
        this.model = model; // Array
        this.view = view;
    }

    public void RunGame(Model model, View view){
        Redraw(model);
        //while true?
        //TODO: Get from user input
        int robotID;
        int direction;
        Move(robotID, direction);

    }
    // No init: redraw everytime
    /*public void initView(model, view){
        for(int row; row < SIZE; row++){
            for(int col; col < SIZE; col++){
                tile = model.getTile(row, col);
                view.drawTile(row, col, tile);
            } // Cols
        } // Rows
    } */

    private int[] Move(int robotID, int direction){
        int tilesMoved = 0;
        boolean[] tileHasWalls = new boolean[4];
        int[] currentPosition = model.findRobot(robotID);
        boolean canMove = true;
        switch(direction){
            case NORTH :
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWallsOfTile(row + tilesMoved + 1, col);
                    wallBlocks = tileHasWalls[2];
                    tileHasRobot = model.tileHasRobot(row + tilesMoved + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                int[] newPosition = {row + tilesMoved, col};
            case EAST:
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWalls(row, col + tilesMoved + 1);
                    wallBlocks = tileHasWalls[3];
                    // Access tile?
                    tileHasRobot = model.hasRobot(row, col + tilesMoved + 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
            case SOUTH:
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWalls(row + tilesMoved + 1, col);
                    wallBlocks = tileHasWalls[1];
                    // Access tile?
                    tileHasRobot = model.hasRobot(row + tilesMoved + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                int[] newPosition = {row - tilesMoved, col};
            case WEST:
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWalls(row, col + tilesMoved + 1);
                    wallBlocks = tileHasWalls[2];
                    // Access tile?
                    tileHasRobot = model.hasRobot(row, col + tilesMoved + 1);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                int[] newPosition = {row, col - tilesMoved};
        }
        updateBoard(model,(robotID, newPosition);
    }

    private void updateBoard(int robotID, int[] newPosition){
        model.setRobot(robotID, newPosition);
    }

    private void Redraw(Model model){

    }
}
