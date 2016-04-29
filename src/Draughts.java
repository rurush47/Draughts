
public class Draughts {
	public static void main(String[] args) 
	{
		System.out.println("Hello kurwa!");
		
		Board model = createNewBoard();
		View view = new View();
		Controller controller = new Controller(model, view);
		
		controller.printBoard();
		
		
	}
	
	private static Board createNewBoard()
	{
		Board board = new Board();
		return board;
	}
}

