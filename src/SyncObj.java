import java.io.Serializable;

public class SyncObj implements Serializable{

	private static final long serialVersionUID = 1L;
	private Man[][] board;
	private String gameOver;
	private String test;
	private Vector2 source;
	private Vector2 destination;
	
	SyncObj(String text)
	{
		this.test = text;
	}
	
	SyncObj(Man[][] board, String text)
	{
		this.board = board;
		this.gameOver = text;
	}
	
	SyncObj(Vector2 source, Vector2 destination)
	{
		this.source = source;
		this.destination = destination;
	}
	
	SyncObj(Vector2 source)
	{
		this.source = source;
	}
	
	public String getText()
	{
		return test;
	}
	
	public Man[][] getBoard()
	{
		return board;
	}
	
	public String getGameStatus()
	{
		return gameOver;
	}
	
	public Vector2 getSourceVector()
	{
		return source;
	}
	
	public Vector2 getDestinationVector()
	{
		return destination;
	}
}
