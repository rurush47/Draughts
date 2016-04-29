
public class View 
{
	public void displayBoard(String board)
	{
		System.out.println(board);
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
