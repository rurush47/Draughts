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
	
	Menu(JPanel cards, Controller controller)
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
				try {
					controller.startNewServer();
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
					controller.startNewClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
