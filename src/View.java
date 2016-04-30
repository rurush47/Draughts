import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class View extends JPanel
{

	private static final long serialVersionUID = 1L;
	Controller controller;
	BufferedImage whiteManImage;
	BufferedImage blackManImage;
	Man[][] board;
	
	
	View()
	{
		loadImages();
	}
	
	private void loadImages()
	{
		try
		{
			whiteManImage = ImageIO.read(getClass().getResourceAsStream("/whiteMan.png"));
			blackManImage = ImageIO.read(getClass().getResourceAsStream("/blackMan.png"));		
		}
		catch (IOException ex)
		{
			System.out.println("Error loading sprite.");
		}
	}
	
	@Override 
	public void paintComponent(Graphics g)
    {
        
		g.setColor(Color.GRAY);
        g.fillRect(0, 0, (Board.BOARDSIZE + 1) * 64, ((Board.BOARDSIZE + 1) * 64));
        
        for (int i = 0; i <= (Board.BOARDSIZE) * 64; i += 128)
        {
        	for (int j = 0; j <= (Board.BOARDSIZE) * 64; j += 128)
        	{
        		g.clearRect(i, j, 64, 64);
        	}
        }
        
        for (int i = 64; i <= (Board.BOARDSIZE) * 64; i += 128)
        {
        	for (int j = 64; j <= (Board.BOARDSIZE) * 64; j += 128)
        	{
        		g.clearRect(i, j, 64, 64);
        	}
        }
        
        g.setColor(Color.black);
        
        drawImagesOnBoard(g);
    }
	
	public void printBoard(Man[][] board)
	{	
		for(int j = 7; j >= 0; j--)
		{
			System.out.print(j + "  ");
			for(int i = 0; i < board.length; i++)
			{
				if(board[i][j] != null)
				{
					if (board[i][j].isWhite())
					{
						System.out.print("[o]");						
					}
					else
					{
						System.out.print("[x]");	
					}
				}
				else
				{
					System.out.print("[ ]");
				}
				if (i == 7)
				{
					System.out.println("");
				}
			}
		}
		System.out.println("   ");
		System.out.print("   ");
		System.out.println("|0||1||2||3||4||5||6||7|");
	}
	
	public void drawImagesOnBoard(Graphics g)
	{
		for(int j = 7; j >= 0; j--)
		{
			for(int i = 7; i >= 0; i--)
			{
				if(board[i][7 - j] != null)
				{
					if (board[i][7 - j].isWhite())
					{
						g.drawImage(whiteManImage, i*64, j*64, this);
					}
					else
					{
						g.drawImage(blackManImage, i*64, j*64, this);	
					}
				}
			}
		}
	}
	
	public void updateBoard(Man[][] board)
	{
		this.board = board;
		printBoard(board);
		repaint();
	}

}
