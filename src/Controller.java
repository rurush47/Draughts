import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller extends MouseAdapter
{
	enum Mode{LOCAL, ONLINE};
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
	
	public void moveMan(Vector2 source, Vector2 destination)
	{
		model.moveMan(source, destination);
	}
	
	public void updateView()
	{
		view.updateBoard(model.getBoard());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		boolean gameOver = false;
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
			gameOver = model.moveMan(selectedManPos, clickPos);
			view.updateBoard(model.getBoard());
			if(gameOver)
				view.gameOver();
			state = State.SELECT;
			return;
		}
	}
}
