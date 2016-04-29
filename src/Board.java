
public class Board {
	private String board = "xd";
	
	public String getBoardString()
	{
		return board;
	}
	
	public class Men
	{
		private boolean isWhite;
		private boolean isKing;
		
		public Men(boolean isWhite)
		{
			this.isWhite = isWhite;
			this.isKing = false;
		}
	}
}
