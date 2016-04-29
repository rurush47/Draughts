
public class Board {
	
	private Man[][] board = new Man[8][8];
	
	public Board()
	{
		for(int j = 0; j < board.length; j++)
			for(int i = 0; i < board.length; i++)
			{
				 if(j <= 2)
				 {
					 if ((i + j)%2 == 0)
					 {
						 board[i][j] = new Man(false);
					 }
				 }
				 else if (j >= 5)
				 {
					 if ((i + j)%2 == 0)
					 {
						 board[i][j] = new Man(true);
					 }
				 }
				 else
				 {
					 board[i][j] = null;
				 }
			}
	}
	
	public Man[][] getBoard()
	{
		return board;
	}
}
