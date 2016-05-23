/**
 * Game main class.
 * @author Rurarz
 *
 */
public class Draughts
{
	/**
	 * Main function of the game. Creates necessary objects.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println("Hello!");
		
		View view = new View();
		//model created in controller
		@SuppressWarnings("unused")
		Controller controller = new Controller(null, view);
	}
}

