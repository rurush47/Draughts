import java.lang.Math;

public class Board {
	public static enum Colour{WHITE, BLACK};
	public static int BOARDSIZE = 7;
	private Man[][] board = new Man[BOARDSIZE + 1][BOARDSIZE + 1];
	private Man selected;
	private Colour currentPlayer;
	private boolean beat = false;
	private boolean doubleBeat = false;
	private Vector2 doubleBeatPos;
	private boolean test = true;
	
	public Board()
	{
		if (test)
		{
			for(int j = 0; j < board.length; j++)
				for(int i = 0; i < board.length; i++)
					board[i][j] = null;
			
			board[5][3] = new Man(false);
			board[6][2] = new Man(true);
			//board[2][0] = new Man(false);
			board[6][2].setKing();
		}
		else
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
		
		currentPlayer = Colour.WHITE;
	}
	
	public Man[][] getBoard()
	{
		return board;
	}
	
	public String moveMan(Vector2 source, Vector2 destination, Colour playerCol)
	{
		if(playerCol == currentPlayer)
		{
			return moveMan(source, destination);
		}
		else
			return null;
	}
	
	public String moveMan(Vector2 source, Vector2 destination)
	{
		if(canManMove(source, destination))
		{
			deselectMan();
			
			board[destination.x][destination.y] = board[source.x][source.y];
			board[source.x][source.y] = null;
			
			if(beat)
			{
				beat(source, destination);
				if (doubleBeatCheck(destination))
				{
					doubleBeat = true;
					doubleBeatPos = destination;
				}
				else
				{
					nextTurn();
				}
			}
			else
			{
				nextTurn();
			}
			kingCheck(destination);
			return winCheck();
		}
		else
		{
			deselectMan();
			System.out.println("Can't do it m8");
		}
		return null;
	}
	
	private boolean canManMove(Vector2 source, Vector2 destination)
	{
		Man sourceMan = board[source.x][source.y];
		
		if(!turnCheck(sourceMan))
		{
			return false;
		}
		if(sourceMan.isKing())
		{
			if(kingMoveCheck(source, destination))
				return true;
			else
				return false;
		}
		if (doubleBeat)
		{
			if(!doubleBeat(source, destination))
				return false;
			else
			{
				beat = true;
				doubleBeat = false;
				return true;
			}
		}
		else if(beatCheck())
		{
			if(beatCheckDestination(source, destination))
			{
				beat = true;
				return true;
			}
			else
				return false;
		}
		//if there is no Man to beat
		if(destinationCheck(source, destination))
		{
			return true;
		}
		return false;
	}
	
	private boolean kingMoveCheck(Vector2 source, Vector2 destination)
	{
		if (doubleBeat)
		{
			if(!doubleBeat(source, destination))
				return false;
			else
			{
				beat = true;
				doubleBeat = false;
				return true;
			}
		}
		else if(beatCheck())
		{
			//can king move this destination during beat
			if(kingBeatCheckDestination(source, destination))
			{
				beat = true;
				return true;
			}
			else
				return false;
		}
		//simple move if there is no beat
		if(kingDestinationCheck(source, destination))
		{
			return true;
		}
		return false;
	}
	
	public boolean isMan(Vector2 source, Colour playerCol)
	{
		if(playerCol == currentPlayer)
		{
			return isMan(source);
		}
		else
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
		if(currentPlayer == Colour.WHITE)
		{
			currentPlayer = Colour.BLACK;
		}
		else
		{
			currentPlayer = Colour.WHITE;
		}
	}
	
	private boolean turnCheck(Man currentMan)
	{
		if(currentMan.isWhite() && currentPlayer == Colour.WHITE ||
				!currentMan.isWhite() && currentPlayer == Colour.BLACK)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean destinationCheck(Vector2 source, Vector2 destination)
	{
		if (board[source.x][source.y] == null)
		{
			return false;
		}
		
		boolean white = board[source.x][source.y].isWhite();
		
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
		Man currentMan;
		for(int i = 0; i <= BOARDSIZE; i++)
		{
			for(int j = 0; j <= BOARDSIZE; j++)
			{
				currentMan = board[i][j];
				
				
				if(currentMan != null)
				{
					if(currentPlayer == Colour.WHITE)
					{
						if(currentMan.isWhite())
						{
							if(currentMan.isKing() && singleBeatKingCheck(new Vector2(i,j)))
							{
								return true;
							}
							else if(singleBeatCheck(new Vector2(i,j)))
							{
								return true;
							}
						}
					}
					else
					{
						if(!currentMan.isWhite())
						{
							if(currentMan.isKing() && singleBeatKingCheck(new Vector2(i,j)))
							{
								return true;
							}
							else if(singleBeatCheck(new Vector2(i,j)))
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
	
	private boolean singleBeatCheck(Vector2 source)
	{	
		if (board[source.x][source.y] == null)
		{
			return false;
		}
		
		boolean white = board[source.x][source.y].isWhite();
		
		for (int k = 1; k >= -1; k -= 2)
		{
			for (int l = 1; l >= -1; l -= 2)
			{
				if (source.x + 2*k >= 0 && source.y + 2*l >= 0 &&
						source.x + 2*k <= BOARDSIZE && source.y + 2*l <= BOARDSIZE)
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
	
	private boolean beatCheckDestination(Vector2 source, Vector2 destination)
	{
		if (board[source.x][source.y] == null)
		{
			return false;
		}
		
		boolean white = board[source.x][source.y].isWhite();
		
		for (int k = 1; k >= -1; k -= 2)
		{
			for (int l = 1; l >= -1; l -= 2)
			{
				if (source.x + 2*k >= 0 && source.y + 2*l >= 0 &&
						source.x + 2*k <= BOARDSIZE && source.y + 2*l <= BOARDSIZE)
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
	
	private void beat(Vector2 source, Vector2 destination)
	{
		int k = (int) Math.signum(destination.x - source.x);
		int l = (int) Math.signum(destination.y - source.y);
		
		int i = source.x + k;
		int j = source.y + l;
		
		while (i != destination.x && j != destination.y)
		{			
			board[i][j] = null;
			i += k;
			j += l;
		}
		beat = false;
	}
	
	private void kingCheck(Vector2 destination)
	{
		Man destinationMan = board[destination.x][destination.y];
		if (destinationMan != null)
		{
			if(destinationMan.isWhite())
			{
				if(destination.y == 7)
					destinationMan.setKing();
			}
			else
			{
				if(destination.y == 0)
					destinationMan.setKing();
			}
		}
	}
	
	private boolean singleBeatKingCheck(Vector2 source)
	{
		if (board[source.x][source.y] == null)
		{
			return false;
		}
	
		boolean white = board[source.x][source.y].isWhite();
		boolean enemyManEncountered = false;

		int i = source.x;
		int j = source.y;
		
		for(int k = 1; k >= -1; k -= 2)
		{
			for(int l = 1; l >= -1; l -= 2)
			{
				enemyManEncountered = false;
				i += k;
				j += l;
				while(i >= 0 && j >= 0 && j <= BOARDSIZE && i <= BOARDSIZE)
				{
					if(board[i][j] != null)
					{	
						if(enemyManEncountered)
						{
							break;
						}
						if(board[i][j].isWhite() != white)
						{
							enemyManEncountered = true;
						}
						if(board[i][j].isWhite() == white)
						{
							break;
						}
					}
					else
					{
						if(enemyManEncountered)
						{
 							System.out.print("damka ma bicie");
							return true;
						}
					}
					i += k;
					j += l;
				}
				i = source.x;
				j = source.y;
			}
		}
		return false;
	}
	
	private boolean kingBeatCheckDestination(Vector2 source, Vector2 destination)
	{
		boolean white = board[source.x][source.y].isWhite();
		int k = (int) Math.signum(destination.x - source.x);
		int l = (int) Math.signum(destination.y - source.y);
		int i = source.x;
		int j = source.y;
		boolean enemyManEncountered = false;
		
		i += k;
		j += l;
		while(i >= 0 && j >= 0 && j <= BOARDSIZE && i <= BOARDSIZE)
		{
			if(board[i][j] != null)
			{	
				if(enemyManEncountered)
				{
					enemyManEncountered = false;
					break;
				}
				if(board[i][j].isWhite() != white)
				{
					enemyManEncountered = true;
				}
				if(board[i][j].isWhite() == white)
				{
					enemyManEncountered = false;
					break;
				}
			}
			else
			{
				if(enemyManEncountered)
				{
					if(destination.x == i && destination.y == j)
						return true;
				}
			}
			i += k;
			j += l;
		}
		return false;
	}
	
	private boolean kingDestinationCheck(Vector2 source, Vector2 destination)
	{
		int k = (int) Math.signum(destination.x - source.x);
		int l = (int) Math.signum(destination.y - source.y);
		int i = source.x;
		int j = source.y;
		
		i += k;
		j += l;
		while(i >= 0 && j >= 0 && j <= BOARDSIZE && i <= BOARDSIZE)
		{
			if(board[i][j] == null)
			{	
				if (destination.x == i && destination.y == j)
					return true;
			}
			else
			{
				return false;
			}
			i += k;
			j += l;
		}
		return false;
	}
	
	private boolean doubleBeat(Vector2 source, Vector2 destination)
	{
		if(!(source.x == doubleBeatPos.x && source.y == doubleBeatPos.y))
			return false;
		
		if(board[source.x][source.y].isKing())
		{
			return kingBeatCheckDestination(source, destination);
		}
		else
		{
			return beatCheckDestination(source, destination);
		}
	}
	
	private boolean doubleBeatCheck(Vector2 source)
	{
		if(board[source.x][source.y].isKing())
		{
			return singleBeatKingCheck(source);
		}
		else
		{
			return singleBeatCheck(source);
		}
	}
	
	private String winCheck()
	{
		boolean whichColour = true;
		boolean firstFlag = true;
		
		for (int i = 0; i <= BOARDSIZE; i++)
			for (int j = 0; j <= BOARDSIZE; j++)
			{
				if (board[i][j] != null)
				{
					if (firstFlag)
					{
						whichColour = board[i][j].isWhite();
						firstFlag = false;
					}
					if(board[i][j].isWhite() != whichColour)
					{
						return null;
					}
				}
			}
		if(whichColour == true)
		{
			return "white_win";
		}
		else
			return "black_win";
	}
}
