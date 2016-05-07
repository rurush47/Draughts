

public class Board {
	private enum Player{WHITE, BLACK};
	public static int BOARDSIZE = 7;
	private Man[][] board = new Man[BOARDSIZE + 1][BOARDSIZE + 1];
	private Man selected;
	private Player currentPlayer;
	
	
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
		currentPlayer = Player.WHITE;
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
			deselectMan();
			tmp = board[source.x][source.y];
			board[source.x][source.y] = null;
			board[destination.x][destination.y] = tmp;
			nextTurn();
		}
		else
		{
			deselectMan();
			System.out.println("Can't do it m8");
		}
	}
	
	private boolean canManMove(Vector2 source, Vector2 destination)
	{
		Man sourceMan = board[source.x][source.y];
		if(!turnCheck(sourceMan))
		{
			return false;
		}
		
		if (destination.x <= BOARDSIZE && destination.x >= 0 &&
			destination.y <= BOARDSIZE && destination.y >= 0)
		{
			if (sourceMan.isWhite())
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
	
	public boolean isMan(Vector2 source)
	{
		Man pointedMan = board[source.x][source.y];
		if (pointedMan != null && turnCheck(pointedMan))
		{
			deselectMan();
			pointedMan.select();
			selected = pointedMan;
			return true;
		}
		else
		{
			deselectMan();
			return false;
		}
	}
	
	private void deselectMan()
	{
		if(selected != null)
		{
			selected.deselect();
		}
	}
	
	private void nextTurn()
	{
		if(currentPlayer == Player.WHITE)
		{
			currentPlayer = Player.BLACK;
		}
		else
		{
			currentPlayer = Player.WHITE;
		}
	}
	
	private boolean turnCheck(Man currentMan)
	{
		if(currentMan.isWhite() && currentPlayer == Player.WHITE ||
				!currentMan.isWhite() && currentPlayer == Player.BLACK)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
