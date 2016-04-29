
public class Controller 
{
	private Board model;
	private View view;
	
	public Controller(Board model, View view)
	{
		this.model = model;
		this.view = view;
	}
	
	public String getBoardString()
	{
		return model.getBoardString();
	}
	
	public void updateView()
	{
		view.displayBoard(model.getBoardString());
	}
}
