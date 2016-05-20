import java.io.Serializable;

public class SyncObj implements Serializable{

	private static final long serialVersionUID = 1L;
	private Man[][] board;
	private boolean gameOver;
	private String test;
	
	SyncObj(Man[][] board, boolean gameOver)
	{
		this.board = board;
		this.gameOver = gameOver;
	}
	
	SyncObj(Man[][] board, boolean gameOver, String text)
	{
		this.board = board;
		this.gameOver = gameOver;
		this.test = text;
	}
	
	public String getText()
	{
		return test;
	}
	
	public Man[][] getBoard()
	{
		return board;
	}
	
	public boolean getGameStatus()
	{
		return gameOver;
	}
}
