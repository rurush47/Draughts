import java.awt.Button;
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
	
	
	public Controller(Board model, View view)
	{
		this.model = model;
		this.view = view;
		this.menu = view.getMenu();
		playButton = view.getMenu().getPlayButton();
		quitButton = menu.getQuit();
		hostButton = menu.getHostButton();
		joinButton = menu.getJoinButton();
		menuButtonsInit();
        view.addMouseListener(this);
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
		Thread t = new ServerThread(6066, this);
		t.start();
	}

	public void startNewClient() throws Exception {
		Thread t = new Client("localhost");
		System.out.println("Client created");
		t.start();
	}
	
	private void menuButtonsInit()
	{
		playButton.addActionListener(new ActionListener() 
		{
			  public void actionPerformed(ActionEvent evt) 
			  {
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
					startNewServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  } 
	    });
		
		joinButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			  {
				try {
					startNewClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
	    });
	}
	
}
