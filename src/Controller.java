import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller extends MouseAdapter
{
	private Board model;
	private View view;
	
	public Controller(Board model, View view)
	{
		this.model = model;
		this.view = view;
	}
	
	public void printBoard()
	{
		view.printBoard(model.getBoard());
	}
	
	public void moveMan(Vector2 source, Vector2 destination)
	{
		model.moveMan(source, destination);
	}
	
	
	public Man[][] getBoard() 
	{
		return model.getBoard();
	}
	
	public void updateView()
	{
		view.updateBoard(model.getBoard());
	}
	



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX() + "," + e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/*
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	*/
}
