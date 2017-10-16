
public class Tile {
	
	private boolean[] walls;
	private Robot robot = null;
	private boolean goal;
	
	/**
	 * 
	 * @param walls - array of boolean, if boolean true, has wall, [N,E,S,W]
	 * @param hasRobot
	 * @param hasGoal
	 */
	public Tile(boolean[] walls, boolean hasRobot, boolean hasGoal) {
		this.walls = walls;
		this.goal = hasGoal;
	}
	
	public boolean[] getWalls() {
		return this.walls;
	}
	
	public Robot getRobot() {
		return this.robot;
	}
	
	public boolean hasGoal() {
		return this.goal;
	}
	
	public void setGoal() {
		this.goal = true;
	}
	
	//Fick error när jag frågade om tiles utan robot hade robot. 
	//Ändrade så att den istället för att fråga efter id till robot som inte existerar istället kollar om robot finns eller är null /N
	public boolean hasRobot() {
		if (this.robot != null) {
			return true;
		}
		else
			return false;
		
	}
	
	public void makeGoal() {
		this.goal = true;
	}
	
	public void setRobot(Robot robot) {
		this.robot = robot;
	}
	
	
	
}
