import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Displays game screen.
 * @author Rurarz
 *
 */
public class View extends JPanel
{
	public static int IMAGESIZE = 64;
	private static final long serialVersionUID = 1L;
	Controller controller;
	BufferedImage whiteManImage;
	BufferedImage blackManImage;
	BufferedImage whiteKingImage;
	BufferedImage blackKingImage;
	BufferedImage manSelected;
	Man[][] board;
	JPanel view;
	Menu menu;
	JPanel cards;
	final String MENU = "Card with Menu";
	final String GAME = "Card with Game";
	
	/*
	 * View constructor.
	 */
	View()
	{
		loadImages();

		cards = new JPanel(new CardLayout());
		
		menu = new Menu(cards);
		
		cards.add(menu.getMenu(), MENU);
		cards.add(this, GAME);
		
		JFrame frame = new JFrame();
		
		frame.getContentPane().setPreferredSize(
        		new Dimension((Board.BOARDSIZE + 1) * View.IMAGESIZE, (Board.BOARDSIZE + 1) * View.IMAGESIZE));
        frame.getContentPane().add(cards);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
	}
	
	/**
	 * Loads images from files.
	 */
	private void loadImages()
	{
		try
		{
			whiteManImage = ImageIO.read(getClass().getResourceAsStream("/whiteMan.png"));
			blackManImage = ImageIO.read(getClass().getResourceAsStream("/blackMan.png"));	
			manSelected = ImageIO.read(getClass().getResourceAsStream("/manSelected.png"));	
			blackKingImage = ImageIO.read(getClass().getResourceAsStream("/blackKing.png"));	
			whiteKingImage = ImageIO.read(getClass().getResourceAsStream("/whiteKing.png"));	
		}
		catch (IOException ex)
		{
			System.out.println("Error loading sprites.");
		}
	}
	
	/**
	 * Draws a board onscreen.
	 */
	@Override 
	public void paintComponent(Graphics g)
    {
        
		g.setColor(Color.GRAY);
        g.fillRect(0, 0, (Board.BOARDSIZE + 1) * IMAGESIZE, ((Board.BOARDSIZE + 1) * IMAGESIZE));
        
        for (int i = 0; i <= (Board.BOARDSIZE) * IMAGESIZE; i += 128)
        {
        	for (int j = 0; j <= (Board.BOARDSIZE) * IMAGESIZE; j += 128)
        	{
        		g.clearRect(i, j, IMAGESIZE, IMAGESIZE);
        	}
        }
        
        for (int i = IMAGESIZE; i <= (Board.BOARDSIZE) * IMAGESIZE; i += 128)
        {
        	for (int j = IMAGESIZE; j <= (Board.BOARDSIZE) * IMAGESIZE; j += 128)
        	{
        		g.clearRect(i, j, IMAGESIZE, IMAGESIZE);
        	}
        }
        
        g.setColor(Color.black);
        
        drawImagesOnBoard(g);
    }
	
	/**
	 * Draws men onscreen.
	 */
	public void drawImagesOnBoard(Graphics g)
	{
		for(int j = 7; j >= 0; j--)
		{
			for(int i = 7; i >= 0; i--)
			{
				if(board[i][7 - j] != null)
				{
					Man currentMan = board[i][7 - j];
					if(currentMan.isSelected())
					{
						g.drawImage(manSelected, i*IMAGESIZE, j*IMAGESIZE, this);
					}
					else if (currentMan.isWhite())
					{
						if(currentMan.isKing())
						{							
							g.drawImage(whiteKingImage, i*IMAGESIZE, j*IMAGESIZE, this);
						}
						else
							g.drawImage(whiteManImage, i*IMAGESIZE, j*IMAGESIZE, this);
					}
					else
					{
						if(currentMan.isKing())
						{							
							g.drawImage(blackKingImage, i*IMAGESIZE, j*IMAGESIZE, this);
						}
						else
							g.drawImage(blackManImage, i*IMAGESIZE, j*IMAGESIZE, this);	
					}
				}
			}
		}
	}
	
	/**
	 * Shows game over message onscreen.
	 * @param player string containing colour of the player who won.
	 */
	public void showWinMessage(String player)
	{
		if(player.equals("white_win"))
		{
			JOptionPane.showMessageDialog(null, "White player wins");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Black player wins");
		}
		returnToMenu();
	}
	
	/**
	 * Shows player disconnect dialog.
	 */
	public void showDisconnectedMessage()
	{
		JOptionPane.showMessageDialog(null, "Player disconnected");
		returnToMenu();
	}
	
	/**
	 * Shows dialog saying that port is already in use.
	 */
	public void portInUseMessage()
	{
		JOptionPane.showMessageDialog(null, "Port already in use.");
		returnToMenu();
	}
	
	/**
	 * Shows dialog saying that there is no game hosted.
	 */
	public void noHostFoundMessage()
	{
		JOptionPane.showMessageDialog(null, "No games hosted.");
	}
	
	/**
	 * Returns to main menu of the game.
	 */
	private void returnToMenu()
	{
		CardLayout cl = (CardLayout)(getCards().getLayout());
	    cl.show(getCards(), MENU);
	}
	
	/**
	 * Repaints the board.
	 * @param board board to draw
	 */
	public synchronized void updateBoard(Man[][] board)
	{
		this.board = board;
		repaint();
		System.out.println("View Updated");
	}

	/*
	 * Returns menu.
	 */
	public Menu getMenu()
	{
		return menu;
	}
	
	/*
	 * Returns views.
	 */
	public JPanel getCards()
	{
		return cards;
	}
	
}
