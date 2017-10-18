/**
 *	The main class which just brings together the MVC
 *	and calls the runGame method inside Controller
 *
 */
public class Robotubbies {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		Controller controller = new Controller();

		//Main-Loop for running game
		controller.runGame();
		
	}

}
