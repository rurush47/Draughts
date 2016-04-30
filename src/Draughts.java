import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;


public class Draughts {
	public static void main(String[] args) 
	{
		System.out.println("Hello kurwa!");
		
		Board model = createNewBoard();
		View view = new View();
		Controller controller = new Controller(model, view);
		
		controller.printBoard();
		
		//start a window
		JFrame frame = new JFrame();
        
        frame.getContentPane().setPreferredSize(
        		new Dimension((Board.BOARDSIZE + 1) * 100, (Board.BOARDSIZE + 1) * 100));
        frame.getContentPane().add(view);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
	}
	
	private static Board createNewBoard()
	{
		Board board = new Board();
		return board;
	}
}

