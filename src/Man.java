
public class Man {
	private boolean isWhite;
	private boolean isKing;
	private boolean selected;
	
	public Man(boolean isWhite)
	{
		this.isWhite = isWhite;
		this.isKing = false;
		this.selected = false;
	}
	
	public boolean isWhite()
	{
		return isWhite;
	}
	
	public boolean isKing()
	{
		return isKing;
	}
	
	public void select()
	{
		selected = true;
	}
	
	public void deselect()
	{
		selected = false;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
}
