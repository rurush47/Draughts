import java.awt.Button;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class Menu {
	private JPanel menu;
	private Button playButton;
	private Button quitButton;
	private Button hostButton;
	private Button joinButton;
	
	Menu(JPanel cards)
	{
		menu = new JPanel();
		playButton = new Button("Play local game");
		quitButton = new Button("Quit");
		hostButton = new Button("Host game");
		joinButton = new Button("Join game");
		
		
		playButton.addActionListener(new ActionListener() 
		{
			  public void actionPerformed(ActionEvent evt) 
			  {
			    CardLayout cl = (CardLayout)(cards.getLayout());
			    cl.next(cards);
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
				if (Server.available(6066))
				{
					try
				      {
				         Thread t = new Server(6066);
				         t.start();
				      }catch(IOException e)
				      {
				         e.printStackTrace();
				      }
				}
				else
				{
					System.out.println("Port unavalible");
				}
			  } 
	    });
		
		joinButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			  {
				try 
				{
					Client.ClientConnect("localhost", 6066);
				} 
				catch (ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
			  }
	    });
		
		menu.add(playButton);
		menu.add(hostButton);
		menu.add(joinButton);
		menu.add(quitButton);
	}
	
	public JPanel getMenu()
	{
		return menu;
	}
}
