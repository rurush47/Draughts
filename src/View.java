
public class View 
{
	public void displayBoard(String board)
	{
		System.out.println(board);
	}
	
	public void printBoard(Man[][] board)
	{
		for(int j = 7; j >= 0; j--)
			for(int i = 0; i < board.length; i++)
			{
				if(board[i][j] != null)
				{
					System.out.print("[x]");
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
}
