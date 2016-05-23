import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;

public class Controller extends MouseAdapter
{
	enum Mode{LOCAL, ONLINE};
	enum State{SELECT, MOVE};
	
	private Board model;
	private View view;
	private State state = State.SELECT;
	private Vector2 selectedManPos;
	private Menu menu;
	private JButton playButton;
	private JButton quitButton;
	private JButton hostButton;
	private JButton joinButton;
	private Mode gameMode;
	private Client client;
	private ServerThread server;
	
	
	public Controller(Board model, View view)
	{
		this.model = model;
		this.view = view;
		this.menu = view.getMenu();
		playButton = menu.getPlayButton();
		quitButton = menu.getQuit();
		hostButton = menu.getHostButton();
		joinButton = menu.getJoinButton();
		menuButtonsInit();
        view.addMouseListener(this);
	}
	
	public String moveMan(Vector2 source, Vector2 destination)
	{
		return model.moveMan(source, destination);
	}
	
	public String moveMan(Vector2 source, Vector2 destination, Board.Colour playerCol)
	{
		return model.moveMan(source, destination, playerCol);
	}
	
	public void updateView()
	{
		view.updateBoard(model.getBoard());
	}
	
	public void updateView(Man[][] board)
	{
		view.updateBoard(board);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Vector2 clickPos = new Vector2(e.getX()/64, (512 - e.getY())/64);
		if(gameMode == Mode.LOCAL)
		{
			mousePressHandle(clickPos);
		}
		else if (gameMode == Mode.ONLINE)
		{
			try {
				client.sendMessage(clickPos);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public synchronized void mousePressHandle(Vector2 clickPos)
	{
		if(gameMode == Mode.LOCAL)
		{
			if(state == State.SELECT && model.isMan(clickPos))
			{
				state = State.MOVE;
				selectedManPos = clickPos;
				updateView();
				return;
			}
			//select another your Man
			if(state == State.MOVE && model.isMan(clickPos))
			{
				selectedManPos = clickPos;
				updateView();
				return;
			}
			else if (state == State.MOVE)
			{
				String gameStatus = moveMan(selectedManPos, clickPos);
				if(gameStatus != null)
				{
					view.showWinMessage(gameStatus);
				}
				updateView();
				state = State.SELECT;
				return;
			}
		}
	}
	
	public void moveHandle(Vector2 clickPos, Board.Colour playerCol) throws IOException
	{
		if(gameMode == Mode.ONLINE)
		{
			System.out.println("Server: move recived");
			if(state == State.SELECT && model.isMan(clickPos, playerCol))
			{
				state = State.MOVE;
				selectedManPos = clickPos;
				System.out.println("Server: man selected");
				broadcastView(model.getBoard(), null);
				return;
			}
			//select another your Man
			if(state == State.MOVE && model.isMan(clickPos, playerCol))
			{
				selectedManPos = clickPos;
				System.out.println("Server: another man selected");
				broadcastView(model.getBoard(), null);
				return;
			}
			else if (state == State.MOVE)
			{
				String gameStatus = moveMan(selectedManPos, clickPos, playerCol);
				if(gameStatus != null)
				{
					broadcastView(model.getBoard(), gameStatus);
				}
				else
				{
					broadcastView(model.getBoard(), null);
				}
				state = State.SELECT;
				return;
			}
		}
	}
	
	public Man[][] getBoard()
	{
		return model.getBoard();
	}

	public void startNewServer() throws IOException {
		server = new ServerThread(6066, this);
		Thread t = server;
		t.start();
	}

	public void startNewClient() throws Exception {
		client = new Client("localhost", this);
		Thread t = client;
		System.out.println("Client created");
		t.start();
	}
	
	private synchronized void broadcastView(Man[][] board, String gameStatus) throws IOException
	{
		SyncObj message = new SyncObj(board, gameStatus);
		server.broadcastMessage(message);
	}
	
	private void menuButtonsInit()
	{
		playButton.addActionListener(new ActionListener() 
		{
			  public void actionPerformed(ActionEvent evt) 
			  {
				gameMode = Mode.LOCAL;
				//create new board and update view
				model = new Board();
				updateView();
				//
			    CardLayout cl = (CardLayout)(view.getCards().getLayout());
			    cl.next(view.getCards());
			  }
	    });
		
		quitButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			  {
				System.exit(0);
			  }
	    });
		
		hostButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			  {
				try {
					gameMode = Mode.ONLINE;
					//create new board and update view
					model = new Board();
					updateView();
					//
					startNewServer();
				} catch (IOException e) {
					view.portInUseMessage();
				}
			  } 
	    });
		
		joinButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			  {
				try {
				    gameMode = Mode.ONLINE;
				    //just to update view
				    model = new Board();
					updateView();
					//
					startNewClient();
					CardLayout cl = (CardLayout)(view.getCards().getLayout());
				    cl.next(view.getCards());
				} catch (Exception e) {
					view.noHostFoundMessage();
				}
			  }
	    });
	}
	
	public void showWinMessage(String player)
	{
		view.showWinMessage(player);
	}
	
	public void handlePlayerDisconnnect()
	{
		view.showDisconnectedMessage();
	}
	
}
