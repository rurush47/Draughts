import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class Draughts
{
	public static void main(String[] args) 
	{
		System.out.println("Hello kurwa!");
		
		View view = new View();
		Board model = createNewBoard();
		Controller controller = new Controller(model, view);
		controller.updateView();


		
		controller.printBoard();
		
		//start a window
		JFrame frame = new JFrame();
        
        frame.getContentPane().setPreferredSize(
        		new Dimension((Board.BOARDSIZE + 1) * 64, (Board.BOARDSIZE + 1) * 64));
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

