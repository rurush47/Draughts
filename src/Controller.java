import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;

/**
 * Controller class. Handles all sorts of input and updates model and view.
 * @author Rurarz
 *
 */
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
	
	/**
	 * Controller constructor.
	 * @param model board reference 
	 * @param view view reference
	 */
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
	
	/**
	 * Sends a move to board to handle.
	 * @param source source click position
	 * @param destination destination position
	 * @return whether game is over
	 */
	public String moveMan(Vector2 source, Vector2 destination)
	{
		return model.moveMan(source, destination);
	}
	
	/**
	 * Sends a move to board to handle. Online version passing calling player colour.
	 * @param source source click position
	 * @param destination destination position
	 * @param playerCol calling player colour
	 * @return whether game is over
	 */
	public String moveMan(Vector2 source, Vector2 destination, Board.Colour playerCol)
	{
		return model.moveMan(source, destination, playerCol);
	}
	
	/**
	 * Calls update method in the view.
	 */
	public void updateView()
	{
		view.updateBoard(model.getBoard());
	}
	
	/**
	 * Calls update method in the view.
	 * @param board board to display in view.
	 */
	public void updateView(Man[][] board)
	{
		view.updateBoard(board);
	}
	
	/**
	 * Listens on a mouse click and calls a mouse click handle.
	 */
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
	
	/**
	 * Handles a received mouse click
	 * @param clickPos position of a mouse click
	 */
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
	
	/**
	 * Handles a mouse click received from online client.
	 * @param clickPos position of a mouse click
	 * @param playerCol calling player colour
	 */
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
	
	/**
	 * Returns current board of men.
	 * @return current board of men
	 */
	public Man[][] getBoard()
	{
		return model.getBoard();
	}

	/**
	 * Starts new server thread.
	 * @throws IOException
	 */
	public void startNewServer() throws IOException {
		server = new ServerThread(6066, this);
		Thread t = server;
		t.start();
	}
	
	/**
	 * Starts new client thread.
	 * @throws Exception
	 */
	public void startNewClient() throws Exception {
		client = new Client("localhost", this);
		Thread t = client;
		System.out.println("Client created");
		t.start();
	}
	
	/**
	 * Calls broadcast method in a server thread
	 * @param board current board of men
	 * @param gameStatus status of a game. null if normal
	 * @throws IOException
	 */
	private synchronized void broadcastView(Man[][] board, String gameStatus) throws IOException
	{
		SyncObj message = new SyncObj(board, gameStatus);
		server.broadcastMessage(message);
	}
	
	/**
	 * Initialise buttons' actions
	 */
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
	
	/**
	 * Calls win game handle in view
	 * @param player string containing colour of a player who won the game.
	 */
	public void showWinMessage(String player)
	{
		view.showWinMessage(player);
	}
	
	/*
	 * Calls disconnected player handle in view
	 */
	public void handlePlayerDisconnnect()
	{
		view.showDisconnectedMessage();
	}
}
