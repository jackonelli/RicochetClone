import java.awt.Point;

public class Robot {
	private int id;
	private String color;
	private Point posish;
	
	public int getId() {
		return this.id;
	}
	public String getColor() {
		return this.color;
	}

	public Point getPosish(){
		return posish;
	}

	public void setPosish(int row, int col){
		posish = new Point(row, col);
	}
	public void setID(int id) {
		this.id = id;
	}
}
