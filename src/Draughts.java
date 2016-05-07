import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Draughts
{
	public static void main(String[] args) 
	{
		System.out.println("Hello kurwa!");
		
		View view = new View();
		Board model = createNewBoard();
		Controller controller = new Controller(model, view);
		controller.updateView();
		JPanel cards;

		final String MENU = "Card with Menu";
		final String GAME = "Card with Game";
		
		//card view
		cards = new JPanel(new CardLayout());
		
		Menu menuObj = new Menu(cards);
		
		cards.add(menuObj.getMenu(), MENU);
		cards.add(view, GAME);
		
		controller.printBoard();
		
		//start a window
		JFrame frame = new JFrame();
        
        frame.getContentPane().setPreferredSize(
        		new Dimension((Board.BOARDSIZE + 1) * View.IMAGESIZE, (Board.BOARDSIZE + 1) * View.IMAGESIZE));
        view.addMouseListener(controller);
        frame.getContentPane().add(cards);
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

