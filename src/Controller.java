import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller extends MouseAdapter
{
	enum State{SELECT, MOVE};
	
	private Board model;
	private View view;
	private State state = State.SELECT;
	private Vector2 selectedManPos;
	
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
	
	public void updateView()
	{
		view.updateBoard(model.getBoard());
	}
	



	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}
    /*
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
     */
	@Override
	public void mousePressed(MouseEvent e) {
		
		Vector2 clickPos = new Vector2(e.getX()/64, (512 - e.getY())/64);
		
		//System.out.print(clickPos.x + ",");
		//System.out.println(clickPos.y);
		
		if(state == State.SELECT && model.isMan(clickPos))
		{
			state = State.MOVE;
			selectedManPos = clickPos;
			view.updateBoard(model.getBoard());
			return;
		}
		//select another your Man
		if(state == State.MOVE && model.isMan(clickPos))
		{
			selectedManPos = clickPos;
			view.updateBoard(model.getBoard());
			return;
		}
		else if (state == State.MOVE)
		{
			model.moveMan(selectedManPos, clickPos);
			view.updateBoard(model.getBoard());
			state = State.SELECT;
			return;
		}
	}
	/*
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	*/
}
