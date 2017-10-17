
public class Tile {
	
	private boolean[] walls;
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
	
	// public Robot getRobot() {
	// 	return this.robot;
	// }
	
	public boolean hasGoal() {
		return this.goal;
	}
	
	public void setGoal() {
		this.goal = true;
	}
	
	//Got error when i asked if tiles without robot had robot 
	//Changed so that it instead checks if robot or null
	// public boolean hasRobot() {
	// 	if (this.robot != null) {
	// 		return true;
	// 	}
	// 	else
	// 		return false;
		
	// }
	
	public void makeGoal() {
		this.goal = true;
	}
	
	// public void setRobot(Robot robot) {
	// 	this.robot = robot;
	// }
	
	
	
}
