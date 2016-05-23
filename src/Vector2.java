import java.io.Serializable;

public class Vector2 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x;	
	public int y;
	
	Vector2(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}