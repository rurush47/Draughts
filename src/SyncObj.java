import java.io.Serializable;

/**
 * Class of an object to send between client and server.
 * @author Rurarz
 *
 */
public class SyncObj implements Serializable{

	private static final long serialVersionUID = 1L;
	private Man[][] board;
	private String gameOver;
	private String test;
	private Vector2 source;
	
	/**
	 * Object text constructor
	 * @param text
	 */
	SyncObj(String text)
	{
		this.test = text;
	}
	
	/*
	 * Object text + board constructor
	 */
	SyncObj(Man[][] board, String text)
	{
		this.board = board;
		this.gameOver = text;
	}
	
	/*
	 * Object single click constructor
	 */
	SyncObj(Vector2 source)
	{
		this.source = source;
	}
	
	/*
	 * Returns object's text.
	 */
	public String getText()
	{
		return test;
	}
	
	/*
	 * Returns object's board.
	 */
	public Man[][] getBoard()
	{
		return board;
	}
	
	/*
	 * Returns object's game status.
	 */
	public String getGameStatus()
	{
		return gameOver;
	}
	
	/*
	 * Returns object's Vector2.
	 */
	public Vector2 getSourceVector()
	{
		return source;
	}
}
