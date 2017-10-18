/**
 * 
 */

/**
 * @author jonathanfager
 *
 *	The main class which just brings together the MVC
 *	and calls the runGame method inside Controller
 *
 */
public class Robotubbies {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		//Get an instance of the View
		View view = new View(16);
		//Get an instance of the Model
		Model model = new Model();
		//Get an instance of the controller
		Controller controller = new Controller(model, view);

		//Main-Loop for running game
		controller.runGame(model, view);
		
	}

}
