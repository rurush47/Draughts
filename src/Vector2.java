import java.io.Serializable;

/**
 * Class representing position of an object on board.
 * @author Rurarz
 *
 */
public class Vector2 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x;	
	public int y;
	
	/*
	 * Vector2 constructor
	 */
	Vector2(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}