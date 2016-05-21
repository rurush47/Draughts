public class Draughts
{
	public static void main(String[] args) 
	{
		System.out.println("Hello!");
		
		View view = new View();
		Board model = new Board();
		Controller controller = new Controller(model, view);
		controller.updateView();    
	}
}

