public class Controller
{
    private static final int SIZE = 16;
    private static final int NUMBER_OF_ROBOTS = 4;
    private Model model(SIZE);
    private View view(SIZE);
    public Controller(Model model, View view){
        this.model = model; // Array
        this.view = view;
    }

    int robotID;
    int direction;
    public void initView(model, view){
        for(int row; row < SIZE; row++){
            for(int col; col < SIZE; col++){
                tile = model.getTile(row, col);
                view.drawTile(row, col, tile);
            } // Cols
        } // Rows
    }

    public int[] move(int robotID, int direction){
        int tilesMoved = 0;
        int[] currentPosition = new int[2];
        int[] newPosition = new int[2];
        currentPosition = model.whereIsRobot(robotID);
        boolean canMove = true;
        boolean[] tileHasWalls = new boolean[4];
        switch(direction){
            // North
            case 1 :
                col = currentPosition[1];
                row = currentPosition[0];
                while(canMove){
                    tileHasWalls = model.getWalls(row + tilesMoved + 1, col);
                    wallBlocks = tileHasWalls[2];
                    // Access tile?
                    tileHasRobot = model.hasRobot(row + tilesMoved + 1, col);
                    if(wallBlocks || tileHasRobot){
                        canMove = false;
                    } else {
                        tilesMoved++;
                    }
                }
                newPosition[0] = row + tilesMoved;
                newPosition[1] = col;
            // East
            case 2:
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
                newPosition[0] = row;
                newPosition[1] = col + tilesMoved;
            // South
            case 3:
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
                newPosition[0] = row - tilesMoved;
                newPosition[1] = col;
            // West
            case 4:
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
                newPosition[0] = row;
                newPosition[1] = col - tilesMoved;
        }
        return newPosition;
    }

    public void updateView(){

    }
}
