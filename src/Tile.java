
public class Tile {
	
	private boolean[] walls;
	private Robot robot;
	private boolean goal;
	
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
	
	public boolean hasRobot() {
		if (this.robot.getId() >= 0) {
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
