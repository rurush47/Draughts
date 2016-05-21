import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;


public class Controller extends MouseAdapter
{
	enum Mode{LOCAL, ONLINE};
	enum State{SELECT, MOVE};
	
	private Board model;
	private View view;
	private State state = State.SELECT;
	private Vector2 selectedManPos;
	private Player whitePlayer;
	private Player blackPlayer;
	private Client client;
	private ServerSocket serverSocket;
	
	
	public Controller(Board model, View view)
	{
		this.model = model;
		this.view = view;
	}
	
	public synchronized boolean moveMan(Vector2 source, Vector2 destination)
	{
		return model.moveMan(source, destination);
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
			moveMan(selectedManPos, clickPos);
			view.updateBoard(model.getBoard());
			state = State.SELECT;
			return;
		}
	}
	
	public Man[][] getBoard()
	{
		return model.getBoard();
	}

	public void startNewServer() throws IOException {
		serverSocket = new ServerSocket(6066);
		
		whitePlayer = new Player(serverSocket.accept(), Board.Colour.WHITE, this);
		blackPlayer = new Player(serverSocket.accept(), Board.Colour.BLACK, this);
		
		whitePlayer.start();
		blackPlayer.start();
	}

	public void startNewClient() throws Exception {
		client = new Client("localhost");
	}
}
