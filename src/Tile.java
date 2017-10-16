
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
	
	//Fick error n�r jag fr�gade om tiles utan robot hade robot. 
	//�ndrade s� att den ist�llet f�r att fr�ga efter id till robot som inte existerar ist�llet kollar om robot finns eller �r null /N
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
