

public class Board {
	
	public static int BOARDSIZE = 7;
	
	private Man[][] board = new Man[BOARDSIZE + 1][BOARDSIZE + 1];
	
	public Board()
	{
		for(int j = 0; j < board.length; j++)
			for(int i = 0; i < board.length; i++)
			{
				 if(j < ((BOARDSIZE + 1)/2 - 1))
				 {
					 if ((i + j)%2 == 0)
					 {
						 board[i][j] = new Man(true);
					 }
				 }
				 else if (j > ((BOARDSIZE + 1)/2))
				 {
					 if ((i + j)%2 == 0)
					 {
						 board[i][j] = new Man(false);
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
	
	public void moveMan(Vector2 source, Vector2 destination)
	{
		Man tmp;
		
		if(canManMove(source, destination))
		{
			tmp = board[source.x][source.y];
			board[source.x][source.y] = null;
			board[destination.x][destination.y] = tmp;
		}
		else
		{
			System.out.println("Can't do it m8");
		}
	}
	
	private boolean canManMove(Vector2 source, Vector2 destination)
	{
		if (destination.x <= BOARDSIZE && destination.x >= 0 &&
			destination.y <= BOARDSIZE && destination.y >= 0)
		{
			if (board[source.x][source.y].isWhite())
			{
				if((destination.x == source.x + 1 || destination.x == source.x - 1) &&
				  (destination.y == source.y + 1))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if((destination.x == source.x + 1 || destination.x == source.x - 1) &&
				  (destination.y == source.y - 1))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}

}
