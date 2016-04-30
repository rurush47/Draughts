import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class View extends JPanel
{
	public void displayBoard(String board)
	{
		System.out.println(board);
	}
	
	@Override 
	public void paintComponent(Graphics g)
    {
        
		g.setColor(Color.GRAY);
        g.fillRect(0, 0, (Board.BOARDSIZE + 1) * 100, ((Board.BOARDSIZE + 1) * 100));
        
        for (int i = 0; i <= (Board.BOARDSIZE) * 100; i += 200)
        {
        	for (int j = 100; j <= (Board.BOARDSIZE) * 100; j += 200)
        	{
        		g.clearRect(i, j, 100, 100);
        	}
        }
        
        for (int i = 100; i <= (Board.BOARDSIZE) * 100; i += 200)
        {
        	for (int j = 0; j <= (Board.BOARDSIZE) * 100; j += 200)
        	{
        		g.clearRect(i, j, 100, 100);
        	}
        }
        
        g.setColor(Color.black);
        g.fillOval(50, 50, 25, 25);
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
}
