import java.io.Serializable;

/*
 * Class representing a single man.
 */
public class Man implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean isWhite;
	private boolean isKing;
	private boolean selected;
	
	/*
	 * Constructor of a man.
	 */
	public Man(boolean isWhite)
	{
		this.isWhite = isWhite;
		this.isKing = false;
		this.selected = false;
	}
	
	/*
	 * Checks if a man is white.
	 */
	public boolean isWhite()
	{
		return isWhite;
	}
	
	/*
	 * Checks if a man is a king.
	 */
	public boolean isKing()
	{
		return isKing;
	}
	
	/*
	 * Selects a man.
	 */
	public void select()
	{
		selected = true;
	}
	
	/*
	 * Deselects a man.
	 */
	public void deselect()
	{
		selected = false;
	}
	
	/*
	 * Checks if this man is selected.
	 */
	public boolean isSelected()
	{
		return selected;
	}
	
	/*
	 * Turns man into a king! 
	 */
	public void setKing()
	{
		isKing = true;
	}
}
