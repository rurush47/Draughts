import java.util.ArrayList;
import java.util.List;

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
		boolean isWhite = sourceMan.isWhite();
		
		if(!turnCheck(sourceMan))
		{
			return false;
		}
		if(beatCheck())
		{
			if(beatCheckDestination(source, destination, isWhite))
			{
				return true;
			}
			else
				return false;
		}
		if(destinationCheck(source, destination, isWhite))
		{
			return true;
		}
		return false;
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
	
	private boolean destinationCheck(Vector2 source, Vector2 destination, boolean white)
	{
		if (destination.x <= BOARDSIZE && destination.x >= 0 &&
				destination.y <= BOARDSIZE && destination.y >= 0)
		{
			if (white)
			{
				if((destination.x == source.x + 1 || destination.x == source.x - 1) &&
				  (destination.y == source.y + 1) && board[destination.x][destination.y] == null)
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
				  (destination.y == source.y - 1) && board[destination.x][destination.y] == null)
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
	
	private boolean beatCheck()
	{
		for(int i = 0; i <= BOARDSIZE; i++)
		{
			for(int j = 0; j <= BOARDSIZE; j++)
			{
				if(board[i][j] != null)
				{
					if(currentPlayer == Player.WHITE)
					{
						if(board[i][j].isWhite())
						{
							if(singleBeatCheck(new Vector2(i,j), true))
							{
								return true;
							}
						}
					}
					else
					{
						if(!board[i][j].isWhite())
						{
							if(singleBeatCheck(new Vector2(i,j), false))
							{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean singleBeatCheck(Vector2 source ,boolean white)
	{	
		if(source.x > 1 && source.y > 1 && source.x < BOARDSIZE - 1 && source.y < BOARDSIZE - 1)
		{
			for (int k = 1; k >= -1; k -= 2)
			{
				for (int l = 1; l >= -1; l -= 2)
				{
					if(board[source.x + k][source.y + l] != null)
					{
						if(board[source.x + k][source.y + l].isWhite() != white && 
								board[source.x + 2*k][source.y + 2*l] == null)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean beatCheckDestination(Vector2 source, Vector2 destination, boolean white)
	{
		//check all directions
		
		if(source.x > 1 && source.y > 1 && source.x < BOARDSIZE - 1 && source.y < BOARDSIZE - 1)
		{
			for (int k = 1; k >= -1; k -= 2)
			{
				for (int l = 1; l >= -1; l -= 2)
				{
					if(board[source.x + k][source.y + l] != null)
					{
						if(board[source.x + k][source.y + l].isWhite() != white && 
								board[source.x + 2*k][source.y + 2*l] == null)
						{
							if(source.x + 2*k == destination.x && source.y + 2*l == destination.y)
								return true;
						}
					}
				}
			}
		}
		return false;
	}
}
